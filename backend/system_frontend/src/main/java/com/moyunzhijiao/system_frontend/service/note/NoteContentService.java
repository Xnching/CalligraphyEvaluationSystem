package com.moyunzhijiao.system_frontend.service.note;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.entity.note.NoteContent;
import com.moyunzhijiao.system_frontend.mapper.note.NoteContentMapper;
import lombok.Data;
import org.springframework.stereotype.Service;


@Service
public class NoteContentService extends ServiceImpl<NoteContentMapper, NoteContent> {

}
