package com.moyunzhijiao.system_frontend.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Data;

@Data
@TableName(value="teacher_homework")
public class TeacherHomework {
    @MppMultiId
    @TableField("teacher_Id")
    Integer teacherId;
    @MppMultiId
    @TableField("homework_Id")
    Integer homeworkId;
    @TableField("template_Id")
    Integer templateId;
    @TableField("template_type")
    String templateType;
}
