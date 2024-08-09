package com.moyunzhijiao.system_frontend.entity.competition;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "competition")
public class Competition {
    Integer id;
    String name;
    @TableField("registration_start_time")
    String registrationStartTime;
    @TableField("registration_end_time")
    String registrationEndTime;
    @TableField("competition_start_time")
    String competitionStartTime;
    @TableField("competition_end_time")
    String competitionEndTime;
    @TableField("review_start_time")
    String reviewStartTime;
    @TableField("review_end_time")
    String reviewEndTime;
    String state;
    @TableField("created_time")
    String createdTime;
}
