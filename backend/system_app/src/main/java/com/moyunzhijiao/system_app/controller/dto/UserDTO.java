package com.moyunzhijiao.system_app.controller.dto;

import lombok.Data;

@Data
public class UserDTO {
    Integer id;
    String name;
    String studentNumber;       //学籍号
    String password;

    Integer klassId;
    String klass;   //
    Integer gradeId;
    String grade;   //
    Integer schoolId;
    String school;  //

    String token;           //token

    String phone;
    String email;
    Integer regionId;
    String region;  //

    String idNumber;        //身份证号
    String gender;
    String pictureUrl;
}
