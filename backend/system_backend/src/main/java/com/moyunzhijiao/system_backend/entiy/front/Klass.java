package com.moyunzhijiao.system_backend.entiy.front;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value="klass")
public class Klass {
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;
    String name;
    @TableField("student_count")
    Integer studentCount;
    @TableField("teacher_id")
    Integer teacherId;
    @TableField("grade_id")
    Integer gradeId;
    @TableField("school_id")
    Integer schoolId;
    String year;
    @TableField("delete_flag")
    boolean deleteFlag;
    @TableField("created_time")
    String createdTime;
}
