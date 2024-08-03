package com.moyunzhijiao.system_frontend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value="school")
public class School {
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;
    String name;
    String type;
    String leader;
    String address;
    @TableField(value="leader_phone")
    String leaderPhone;
    @TableField(value="person_count")
    Integer personCount;
    @TableField(value="region_id")
    Integer regionId;
    @TableField(value="created_time")
    String createdTime;
    @TableField(value="delete_flag")
    boolean deleteFlag;

}