package com.moyunzhijiao.system_backend.controller.dto.front;

import lombok.Data;

@Data
public class TeacherDTO {
    Integer id;
    String name;
    String phone;
    String password;
    String idNumber;
    Integer schoolId;
    String school;
    Integer finishReviewed;     //已评阅完的作品数
    String workno;
}
