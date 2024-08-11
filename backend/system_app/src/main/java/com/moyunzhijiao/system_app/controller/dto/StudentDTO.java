package com.moyunzhijiao.system_app.controller.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class StudentDTO {
    Integer id;
    String name;
    String studentNumber;       //学籍号
    String password;
    Integer klassId;
    String klass;
    String token;
    Integer schoolId;
    String school;
    Integer gradeId;
    String grade;
    String phone;
    String email;
    Integer regionId;
    String region;
    String idNumber;        //身份证号
    String gender;
    String pictureUrl;
}
