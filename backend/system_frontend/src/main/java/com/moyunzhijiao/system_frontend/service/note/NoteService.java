package com.moyunzhijiao.system_frontend.service.note;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.common.Constants;
import com.moyunzhijiao.system_frontend.controller.dto.NoteDTO;
import com.moyunzhijiao.system_frontend.entity.note.Note;
import com.moyunzhijiao.system_frontend.entity.note.NoteContent;
import com.moyunzhijiao.system_frontend.exception.ServiceException;
import com.moyunzhijiao.system_frontend.mapper.note.NoteContentMapper;
import com.moyunzhijiao.system_frontend.mapper.note.NoteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteService extends ServiceImpl<NoteMapper, Note> {

    @Autowired
    NoteMapper noteMapper;
    @Autowired
    NoteContentMapper noteContentMapper;
    @Autowired
    NoteContentService noteContentService;
    public void insertSingle(Note note, NoteContent noteContent) {
        noteMapper.insert(note);
        noteContent.setNoteId(note.getId());
        noteContentMapper.insert(noteContent);
    }

    public IPage<NoteDTO> getSysMsgOfStu(Integer currentPage,Integer pageSize){
        IPage<NoteDTO> page =new Page<>(currentPage,pageSize);
        Integer total;
        page= noteMapper.getSysMsgOfStu(page);
        total= noteMapper.countSysMsgOfStu();
        page.setTotal(total);
        return page;
    }
    public IPage<NoteDTO> getComMsgOfStu(Integer currentPage,Integer pageSize){
        IPage<NoteDTO> page =new Page<>(currentPage,pageSize);
        Integer total;
        page= noteMapper.getComMsgOfStu(page);
        total= noteMapper.countComMsgOfStu();
        page.setTotal(total);
        return page;
    }
    public IPage<NoteDTO> getWorkReleaseMsgOfStu(Integer currentPage,Integer pageSize,Integer stuId,Integer klassId){
        IPage<NoteDTO> page =new Page<>(currentPage,pageSize);
        Integer total;
        page= noteMapper.getWorkReleaseMsgOfStu(page,stuId,klassId);
        total= noteMapper.countWorkReleaseMsgOfStu(stuId,klassId);
        page.setTotal(total);
        return page;
    }
    public IPage<NoteDTO> getWorkRemindMsgOfStu(Integer currentPage,Integer pageSize,Integer stuId,Integer klassId){
        IPage<NoteDTO> page =new Page<>(currentPage,pageSize);
        Integer total;
        page= noteMapper.getWorkRemindMsgOfStu(page,stuId,klassId);
        total= noteMapper.countWorkRemindMsgOfStu(stuId,klassId);
        page.setTotal(total);
        return page;
    }

    public IPage<NoteDTO> searchMsgOfStu(String keyword, Integer stuId, Integer klassId, Integer currentPage, Integer pageSize) {
        // 1. 获取所有符合条件的 page
        IPage<NoteDTO> sysMsgPage = this.getSysMsgOfStu( currentPage, pageSize);
        IPage<NoteDTO> comMsgPage = this.getComMsgOfStu( currentPage, pageSize);
        IPage<NoteDTO> workReleasePage = this.getWorkReleaseMsgOfStu( currentPage, pageSize,stuId,klassId);
        IPage<NoteDTO> workRemindPage = this.getWorkRemindMsgOfStu( currentPage, pageSize,stuId,klassId);

        // 2. 合并 page，并将结果存储在新的 page 中
        IPage<NoteDTO> mergedPage = new Page<>(currentPage, pageSize);
        List<NoteDTO> mergedNotes = new ArrayList<>();
        mergedNotes.addAll(sysMsgPage.getRecords());
        mergedNotes.addAll(comMsgPage.getRecords());
        mergedNotes.addAll(workReleasePage.getRecords());
        mergedNotes.addAll(workRemindPage.getRecords());
        
        // 3. 对合并后的结果进行模糊搜索
        Collectors Collectors = null;
        List<NoteDTO> filteredNotes = mergedNotes.stream()
                .filter(note -> note.getName().toLowerCase().contains(keyword.toLowerCase())) // 模糊匹配
                .collect(Collectors.toList());

        mergedPage.setRecords(filteredNotes);
        mergedPage.setTotal(filteredNotes.size()); // 更新总数

        return mergedPage;
    }

    /*
    * 基础的消息都用此接收，例如竞赛消息和系统消息为基础消息
    * @params: type 消息类型
    * 注：target全为全体，将不做区分
    * */
    public IPage<Note> getCommonMsg(String type, IPage<Note> noteIPage) {
        if((!type.equals("竞赛消息"))&&(!type.equals("系统消息"))){
            throw new ServiceException(Constants.CODE_401,"消息类型错误！");
        }
        QueryWrapper<Note> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",type);

        return page(noteIPage,queryWrapper);
    }

    /*
    * 获取消息详情
    * */
    public NoteDTO getMessageDetail(Integer msgId) {
        Note note = getById(msgId);
        NoteDTO noteDTO = new NoteDTO();
        NoteContent noteContent = noteContentService.getById(msgId);
        BeanUtil.copyProperties(note,noteDTO);
        noteDTO.setContent(noteContent.getMessage());
        return noteDTO;
    }

    @Transactional
    public Integer deleteByHomework(Integer homeworkId) {
        QueryWrapper<Note> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type","布置作业");
        queryWrapper.eq("association_id",homeworkId);
        Note note = noteMapper.selectOne(queryWrapper);
        removeById(note);
        noteContentService.deleteByNote(note.getId());
        return note.getId();
    }
}
