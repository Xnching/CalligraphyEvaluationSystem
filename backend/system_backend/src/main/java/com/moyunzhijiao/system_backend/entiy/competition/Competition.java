package com.moyunzhijiao.system_backend.entiy.competition;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName("competition")
public class Competition {
    @TableId(type = IdType.AUTO)
    Integer id;
    String name;
    String registrationStartTime;
    String registrationEndTime;
    String competitionStartTime;
    String competitionEndTime;
    String reviewStartTime;
    Integer count;
    String state;
    boolean deleteFlag;
    private String picture;
    String createdTime;
    @TableField(exist = false)
    List<Division> groups;
    @TableField(exist = false)
    String requirement;

}
