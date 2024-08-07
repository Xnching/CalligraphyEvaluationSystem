package com.moyunzhijiao.system_frontend.entity.template;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Data;

@Data
@TableName(value = "teacher_template")
public class TeacherTemplate {
    @MppMultiId
    @TableField("teacher_id")
    Integer teacherId;
    @MppMultiId
    @TableField("template_id")
    Integer templateId;
    Integer count;
}
