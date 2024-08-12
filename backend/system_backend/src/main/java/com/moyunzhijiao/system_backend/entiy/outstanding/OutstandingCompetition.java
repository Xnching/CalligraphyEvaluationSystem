package com.moyunzhijiao.system_backend.entiy.outstanding;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "outstanding_competition")
public class OutstandingCompetition {
    @TableId(value = "submissions_id",type = IdType.INPUT)
    Integer submissionsId;
    @TableField("created_time")
    String createdTime;
}
