package com.moyunzhijiao.system_frontend.entity.homework;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value="homework_submission")
public class HomeworkSubmission {
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;
    @TableField(value="system_score")
    Integer systemScore;
    @TableField(value="system_feedback")
    String systemFeedback;
    @TableField(value="teacher_score")
    Integer teacherScore;
    @TableField(value="teacher_feedback")
    String teacherFeedback;
    String content;
    @TableField(value="submited_time")
    String submitedTime;
    @TableField(value="homework_id")
    Integer homeworkId;
    @TableField(value="student_id")
    Integer studentId;
    boolean state;      //0表示没有提交
    Integer reviewed;   //作业是否被教师批改，0为未批改，1为中间态，2为已批改
}
