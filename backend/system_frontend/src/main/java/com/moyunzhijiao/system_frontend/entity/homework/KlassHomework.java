package com.moyunzhijiao.system_frontend.entity.homework;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Data;

@Data
@TableName(value="klass_homework")
public class KlassHomework {
    @MppMultiId
    Integer klassId;
    @MppMultiId
    Integer homeworkId;
}
