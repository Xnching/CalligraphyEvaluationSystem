package com.moyunzhijiao.system_frontend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Data;

@Data
@TableName(value="teacher_klass_homework")
public class KlassHomework {
    @MppMultiId
    Integer klassId;
    @MppMultiId
    Integer homeworkId;
}
