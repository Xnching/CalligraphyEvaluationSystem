package com.moyunzhijiao.system_frontend.controller.dto;

import lombok.Data;

@Data
public class HomeworkDTO {
    Integer id;
    String name;
    String deadline;
    Integer unSubmit;
    String target;
}
