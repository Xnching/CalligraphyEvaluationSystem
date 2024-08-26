package com.moyunzhijiao.system_backend.entiy.competition;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

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
    @TableField("initial_evaluation")
    String initialEvaluation;     //初级评语
    @TableField("initial_rank")
    Integer initialRank;
    @TableField("system_score")
    Integer systemScore;
    Integer teacherId;
    @TableField("system_evaluation")
    String systemEvaluation;
    @TableField("average_final_score")
    BigDecimal averageFinalScore;
    @TableField("created_time ")
    String createdTime ;
    @TableField(exist = false)
    String level;       //作品等级
    @TableField(exist = false)
    String student;     //学生名称
    @TableField(exist = false)
    List<String> imageList; //作品图片
    @TableField(exist = false)
    Integer finalRank;
    @TableField(exist = false)
    Integer finalScore;     //一个教师评阅该作品所批改的一个教师的最终分数
}
