package com.moyunzhijiao.system_frontend.entity.competition;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "division")
public class Division {
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;
    String name;
    @TableField("competition_id")
    Integer competitionId;
    @TableField("special_prize_ratio")
    Float specialPrizeRatio;
    @TableField("first_prize_ratio")
    Float first_prize_ratio;
    @TableField("second_prize_ratio")
    Float second_prize_ratio;
    @TableField("third_prize_ratio")
    Float third_prize_ratio;
    String target;
    @TableField("created_time")
    String createdTime;
}
