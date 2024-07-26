package com.moyunzhijiao.system_backend.controller.dto;

import lombok.Data;

@Data
public class TeacherDTO {
    Integer id;
    String name;
    String phone;
    String password;
    String idNumber;
    Integer schoolId;
    String workno;
}
