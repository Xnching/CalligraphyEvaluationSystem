package com.moyunzhijiao.system_frontend.entity.note;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class NoteContent {
    @TableId(value = "note_id",type = IdType.INPUT)
    Integer noteId;
    String message;
}
