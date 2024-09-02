package com.moyunzhijiao.system_backend.entiy.front;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "note_content")
public class NoteContent {
    @TableId(value = "note_id",type = IdType.INPUT)
    Integer noteId;
    String message;
}