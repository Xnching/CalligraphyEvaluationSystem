package com.moyunzhijiao.system_frontend.controller;

import cn.hutool.core.bean.BeanUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyunzhijiao.system_frontend.common.Constants;
import com.moyunzhijiao.system_frontend.common.Result;
import com.moyunzhijiao.system_frontend.controller.dto.NoteDTO;
import com.moyunzhijiao.system_frontend.entity.note.Note;
import com.moyunzhijiao.system_frontend.entity.note.NoteContent;
import com.moyunzhijiao.system_frontend.service.note.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoteController {
    @Autowired
    NoteService noteService;

    /*
    * 教师获取消息列表
    * */
    @GetMapping("/ciep/message")
    public Result findMessageOfTeacher(@RequestParam Integer type,
                                       @RequestParam Integer currentPage, @RequestParam Integer pageSize ){
        IPage<Note> noteIPage = new Page<>(currentPage,pageSize);
        if(type==0){
            noteIPage = noteService.getCommonMsg("竞赛消息",noteIPage);
        }else if(type==1){
            noteIPage = noteService.getCommonMsg("系统消息",noteIPage);
        }else {
            return Result.error(Constants.CODE_401,"type内容错误！");
        }
        return Result.success(noteIPage);
    }

    /*
    * 获取消息详情
    * */
    @GetMapping("/ciep/message-detail")
    public Result findMessageDetail(@RequestParam Integer msgId){
        NoteDTO noteDTO = noteService.getMessageDetail(msgId);
        return Result.success(noteDTO);
    }

    @GetMapping("/cieps/sys-message")
    public Result getSysMsgOfStu(@RequestHeader("authorization") String token,
                            @RequestParam Integer currentPage,
                            @RequestParam Integer pageSize){
        DecodedJWT jwt = JWT.decode(token);
        IPage<NoteDTO> page =noteService.getSysMsgOfStu(currentPage,pageSize);
        return Result.success(page);
    }
    @GetMapping("/cieps/competition-message")
    public Result getComMsgOfStu(@RequestHeader("authorization") String token,
                                 @RequestParam Integer currentPage,
                                 @RequestParam Integer pageSize){
        DecodedJWT jwt = JWT.decode(token);
        IPage<NoteDTO> page =noteService.getComMsgOfStu(currentPage,pageSize);
        return Result.success(page);
    }

    @GetMapping("/cieps/work-release-message")
    public Result getWorkReleaseMsgOfStu(@RequestHeader("authorization") String token,
                                 @RequestParam Integer klassId,
                                 @RequestParam Integer currentPage,
                                 @RequestParam Integer pageSize){
        DecodedJWT jwt = JWT.decode(token);
        Integer stuId = Integer.valueOf(jwt.getAudience().get(0));
        IPage<NoteDTO> page =noteService.getWorkReleaseMsgOfStu(currentPage,pageSize,stuId,klassId);
        return Result.success(page);
    }

    @GetMapping("/cieps/work-remind-message")
    public Result getWorkRemindMsgOfStu(@RequestHeader("authorization") String token,
                                         @RequestParam Integer klassId,
                                         @RequestParam Integer currentPage,
                                         @RequestParam Integer pageSize){
        DecodedJWT jwt = JWT.decode(token);
        Integer stuId = Integer.valueOf(jwt.getAudience().get(0));
        IPage<NoteDTO> page =noteService.getWorkRemindMsgOfStu(currentPage,pageSize,stuId,klassId);
        return Result.success(page);
    }

    @GetMapping("/cieps/search-message")
    public Result searchMsgOfStu(@RequestHeader("authorization") String token,
                                        @RequestParam String keyword,
                                        @RequestParam Integer klassId,
                                        @RequestParam Integer currentPage,
                                        @RequestParam Integer pageSize){
        DecodedJWT jwt = JWT.decode(token);
        Integer stuId = Integer.valueOf(jwt.getAudience().get(0));
        IPage<NoteDTO> page =noteService.searchMsgOfStu(keyword,stuId,klassId,currentPage,pageSize);
        return Result.success(page);
    }
}
