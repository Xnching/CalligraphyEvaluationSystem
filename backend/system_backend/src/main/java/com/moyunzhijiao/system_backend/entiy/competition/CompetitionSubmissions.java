package com.moyunzhijiao.system_backend.entiy.competition;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName(value = "competition_submissions")
public class CompetitionSubmissions {
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;
    @TableField("competition_id")
    Integer competitionId;
    @TableField("division_id")
    Integer divisionId;
    @TableField("author_id")
    Integer authorId;
    @TableField("initial_score")
    Integer initialScore;
    @TableField("final_evaluation")
    String finalEvaluation;
    @TableField("initial_rank")
    Integer initial_rank;
    @TableField("system_score")
    Integer systemScore;
    @TableField("system_evaluation")
    Integer systemEvaluation;
    @TableField("average_final_score")
    BigDecimal averageFinalScore;
    @TableField("created_time ")
    String createdTime ;
}
