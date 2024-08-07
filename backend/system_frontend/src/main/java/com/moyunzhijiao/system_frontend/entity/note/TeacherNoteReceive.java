package com.moyunzhijiao.system_frontend.entity.note;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Data;

@Data
@TableName(value = "teacher_note_receive")
public class TeacherNoteReceive {
    @MppMultiId
    Integer noteId;
    @MppMultiId
    Integer teacherId;
}
