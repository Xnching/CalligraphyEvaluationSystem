package com.moyunzhijiao.system_backend.controller.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class StudentDTO {
    @ExcelIgnore
    Integer id;
    @ExcelProperty("学生姓名")
    String name;
    @ExcelProperty("年级")
    String grade;
    @ExcelProperty("班级")
    String klass;
    @ExcelProperty("电话号码")
    String phone;
    @ExcelIgnore
    Integer klassId;
    @ExcelIgnore
    Integer gradeId;
    @ExcelProperty("学籍号")
    String studentNumber;
    @ExcelProperty("密码")
    String password;
    @ExcelProperty("身份证号")
    String idNumber;
    @ExcelIgnore
    Integer schoolId;
    @ExcelProperty("性别")
    String gender;
}
