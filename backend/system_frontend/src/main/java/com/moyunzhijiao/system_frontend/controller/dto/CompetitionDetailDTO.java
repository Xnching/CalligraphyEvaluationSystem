package com.moyunzhijiao.system_frontend.controller.dto;

import com.moyunzhijiao.system_frontend.entity.competition.CsubmissionImage;
import lombok.Data;

import java.util.List;

@Data
public class CompetitionDetailDTO {
    String competitionSubmissionId;
    String competitionName;
    String divisionName;
    String level;
    List<String> pictureUrl;
    Integer systemScore;
    String systemEvaluation;
    Integer finalScore;
}
