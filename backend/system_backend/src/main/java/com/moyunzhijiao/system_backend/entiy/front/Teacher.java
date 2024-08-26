package com.moyunzhijiao.system_backend.entiy.front;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value="teacher")
public class Teacher {
    @TableId(value = "id",type = IdType.AUTO)
    @ExcelIgnore
    Integer id;

    @ExcelProperty("教师姓名")
    String name;

    @ExcelProperty("工号")
    String workno;

    @ExcelProperty("密码")
    String password;

    @TableField("school_id")
    @ExcelIgnore
    Integer schoolId;

    @ExcelProperty("电话号码")
    String phone;

    @ExcelIgnore
    String email;

    @TableField("region_id")
    @ExcelIgnore
    Integer regionId;

    @ExcelProperty("身份证号")
    @TableField("id_number")
    String idNumber;

    @ExcelIgnore
    String gender;

    @ExcelIgnore
    String avatar;

    @TableField("delete_flag")
    @ExcelIgnore
    boolean deleteFlag;

    @TableField("created_time")
    @ExcelIgnore
    String createdTime;

}
