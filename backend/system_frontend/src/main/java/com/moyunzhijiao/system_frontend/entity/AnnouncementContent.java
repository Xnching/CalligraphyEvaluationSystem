package com.moyunzhijiao.system_frontend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "announcement_content")
public class AnnouncementContent {
    @TableId(value = "announcement_id",type = IdType.INPUT)
    Integer announcementId;
    String message;
}