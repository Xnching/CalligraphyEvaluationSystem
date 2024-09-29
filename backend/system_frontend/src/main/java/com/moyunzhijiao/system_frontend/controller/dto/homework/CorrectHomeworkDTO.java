package com.moyunzhijiao.system_frontend.controller.dto.homework;

import lombok.Data;

import java.util.List;

@Data
public class CorrectHomeworkDTO {
    List<Submission> list;
    @Data
    public static class Submission{
        Integer score;
        Integer submissionId;
        String feedback;
    }
}
