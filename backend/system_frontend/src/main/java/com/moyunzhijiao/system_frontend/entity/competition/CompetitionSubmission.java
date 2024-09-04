package com.moyunzhijiao.system_frontend.entity.competition;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value="competition_submissions")
public class CompetitionSubmission {
    Integer id;
    Integer competitionId;
    Integer divisionId;
    Integer authorId;
    Integer initialScore;
    String initialEvaluation;
    Integer systemScore;
    String systemEvaluation;
    Integer averageFinalScore;
    String createdTime;
    Integer initialRank;
    String name;
    Integer teacherId;
}
