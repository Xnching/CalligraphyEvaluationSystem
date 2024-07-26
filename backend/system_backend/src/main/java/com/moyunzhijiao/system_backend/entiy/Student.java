package com.moyunzhijiao.system_backend.entiy;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName(value="student")
public class Student {
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;
    String name;
    @TableField("student_number")
    String studentNumber;
    String password;
    @TableField("klass_id")
    Integer klassId;
    @TableField("school_id")
    Integer schoolId;
    @TableField("grade_id")
    Integer gradeId;
    String phone;
    String email;
    @TableField("region_id")
    Integer regionId;
    @TableField("id_number")
    String idNumber;
    String gender;
    String pictureUrl;
    @TableField("delete_flag")
    boolean deleteFlag;
    @TableField("created_time")
    String createdTime;
}
