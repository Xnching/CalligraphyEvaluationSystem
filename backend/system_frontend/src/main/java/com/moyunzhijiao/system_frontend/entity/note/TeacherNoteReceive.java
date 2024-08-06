package com.moyunzhijiao.system_frontend.entity.note;

import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Data;

@Data
public class TeacherNoteReceive {
    @MppMultiId
    Integer noteId;
    @MppMultiId
    Integer teacherId;
}
