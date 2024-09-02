package com.moyunzhijiao.system_backend.controller.dto.outstanding;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OutstandingCompetitionDTO {
    Integer submissionId;
    String competition;
    String division;
    String author;
    BigDecimal averageFinalScore;
    Integer rk;
    String level;
    String initialEvaluation;
    List<String> imageSrcList;
}
