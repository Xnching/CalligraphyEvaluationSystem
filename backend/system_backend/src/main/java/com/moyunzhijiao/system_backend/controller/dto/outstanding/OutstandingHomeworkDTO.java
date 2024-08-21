package com.moyunzhijiao.system_backend.controller.dto.outstanding;

import lombok.Data;

@Data
public class OutstandingHomeworkDTO {
    Integer submissionId;
    String name;
    String author;
    String teacher;
    String type;
    String grade;
    String reviewer;
}