package com.moyunzhijiao.system_backend.entiy.announcementHelp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "announcement")
public class Announcement {
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;
    String name;
    String target;
    String state;
    @TableField("release_time")
    String releaseTime;
    @TableField(exist = false)
    String releaseType;
    String type;
    @TableField("picture_url")
    String pictureUrl;
    @TableField(exist = false)
    String content;
    String publisher;
    @TableField("created_time")
    String createdTime;
}
