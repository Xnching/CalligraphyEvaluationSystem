package com.moyunzhijiao.system_frontend.controller;

import cn.hutool.core.bean.BeanUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyunzhijiao.system_frontend.common.Constants;
import com.moyunzhijiao.system_frontend.common.Result;
import com.moyunzhijiao.system_frontend.controller.dto.HomeworkDTO;
import com.moyunzhijiao.system_frontend.entity.Homework;
import com.moyunzhijiao.system_frontend.service.HomeworkService;
import com.moyunzhijiao.system_frontend.service.TeacherHomeworkService;
import com.moyunzhijiao.system_frontend.service.note.KlassNoteReceiveService;
import com.moyunzhijiao.system_frontend.service.note.StudentNoteReceiveService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Map;

@RestController
public class HomeworkController {
    @Autowired
    HomeworkService homeworkService;
    @Autowired
    TeacherHomeworkService teacherHomeworkService;
    @Autowired
    KlassNoteReceiveService klassNoteReceiveService;
    @Autowired
    StudentNoteReceiveService studentNoteReceiveService;

    @Operation(summary = "教师的查询作业列表，四个分页查询")
    @GetMapping("/ciep/homework")
    public Result findHomeworkPageOfTeacher(@RequestHeader("authorization") String token
            , @RequestParam Integer type, @RequestParam Integer currentPage, @RequestParam Integer pageSize
            ,@RequestParam(defaultValue = "")String name){
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer teacherId = Integer.valueOf(jwt.getAudience().get(0));
        IPage<HomeworkDTO> page = new Page<>(currentPage,pageSize);
        page = teacherHomeworkService.getHomeworkPageOfTeacher(page,type,teacherId,name);
        return Result.success(page);
    }

    @Operation(summary = "延迟作业")
    @PostMapping("/ciep/postpone")
    public Result postponeHomework(@RequestBody Map<String, String> request){
        String homeworkId = request.get("homeworkId");
        String newDate = request.get("newDate");
        Homework homework = null;
        try {
            homework = homeworkService.getById(homeworkId);
        } catch (Exception e) {
            return Result.error(Constants.CODE_401,"没找到目标作业！");
        }
        try {
            homework.setDeadline(Timestamp.valueOf(newDate));
        } catch (Exception e) {
            return Result.error(Constants.CODE_401,"时间格式不正确！");
        }
        return Result.success();
    }

    @Operation(summary = "催促作业，-1表示没有,type中0表示集体作业催促，1表示个人作业催促")
    @PostMapping("/ciep/urge")
    public Result urgeHomework(@RequestHeader("authorization") String token,@RequestBody Map<String, String> request){
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer teacherId = Integer.valueOf(jwt.getAudience().get(0));
        Integer homeworkId = Integer.valueOf(request.get("homeworkId"));
        //检查作业
        if(homeworkId==-1|| BeanUtil.isEmpty(homeworkId)){
            return Result.error(Constants.CODE_401,"未指定作业！");
        }
        Integer stuId = Integer.valueOf(request.get("stuId"));
        Integer type = Integer.valueOf(request.get("type"));
        try {
            if (stuId!=-1){
                studentNoteReceiveService.urgeHomeworkSingle(teacherId,stuId,homeworkId);
            } else if(stuId==-1) {
                switch (type){
                    case 0:
                        klassNoteReceiveService.urgeHomework(teacherId,homeworkId);
                        break;
                    case 1:
                        studentNoteReceiveService.urgeHomeworkBatch(teacherId,homeworkId);
                        break;
                    default:
                        return Result.error(Constants.CODE_401,"type内容错误！");
                }
            }
        } catch (Exception e) {
            return Result.error(Constants.CODE_500,"系统错误，催促失败！");
        }
        return Result.success();
    }

}
