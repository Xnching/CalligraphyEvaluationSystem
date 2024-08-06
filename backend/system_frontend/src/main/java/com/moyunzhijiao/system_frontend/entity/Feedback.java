package com.moyunzhijiao.system_frontend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value="feedback")
public class Feedback {
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;
    @TableField("provider_id")
    Integer providerId;
    @TableField("provider_type")
    String providerType;
    String state;
    String type;
    @TableField(exist = false)
    String content;     //反馈内容
    @TableField("provider_phone")
    String phone;
    @TableField("created_time")
    String createdTime;

}
