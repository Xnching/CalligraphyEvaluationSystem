package com.moyunzhijiao.system_backend.entiy.competition;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("division")
public class Division {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer competitionId;
    private Float specialPrizeRatio;
    private Float firstPrizeRatio;
    private Float secondPrizeRatio;
    private Float thirdPrizeRatio;
    private String target;
    boolean deleteFlag;
    private String createdTime;
    @TableField(exist = false)
    String requirement;
    @TableField(exist = false)
    Integer teacherNumber;          //参与评阅的教师数
    @TableField(exist = false)
    Integer submissionNumber;       //作品名
    @TableField(exist = false)
    String competitionName;     //竞赛名
    @TableField(exist = false)
    Integer reviewedNumber;     //已评阅作品数
    @TableField(exist = false)
    String state;
}