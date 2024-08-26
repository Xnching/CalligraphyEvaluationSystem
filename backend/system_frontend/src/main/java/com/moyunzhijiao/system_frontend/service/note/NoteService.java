package com.moyunzhijiao.system_frontend.service.note;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.controller.dto.ArticleDTO;
import com.moyunzhijiao.system_frontend.controller.dto.HomeworkDTO;
import com.moyunzhijiao.system_frontend.controller.dto.NoteDTO;
import com.moyunzhijiao.system_frontend.entity.note.Note;
import com.moyunzhijiao.system_frontend.entity.note.NoteContent;
import com.moyunzhijiao.system_frontend.mapper.note.NoteContentMapper;
import com.moyunzhijiao.system_frontend.mapper.note.NoteMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteService extends ServiceImpl<NoteMapper, Note> {

    @Autowired
    NoteMapper noteMapper;
    @Autowired
    NoteContentMapper noteContentMapper;
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
}
