package com.moyunzhijiao.system_frontend.entity.collection;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Data;

@Data
@TableName(value = "tea_works_collection")
public class TeaWorksCollection {
    @MppMultiId
    @TableField("teacher_id")
    Integer teacherId;
    @MppMultiId
    @TableField("submission_id")
    Integer submissionId;
    @MppMultiId
    String type;
    String createdTime;
}
