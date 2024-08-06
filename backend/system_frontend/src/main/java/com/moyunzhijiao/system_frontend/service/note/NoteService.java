package com.moyunzhijiao.system_frontend.service.note;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.entity.note.Note;
import com.moyunzhijiao.system_frontend.entity.note.NoteContent;
import com.moyunzhijiao.system_frontend.mapper.note.NoteContentMapper;
import com.moyunzhijiao.system_frontend.mapper.note.NoteMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
