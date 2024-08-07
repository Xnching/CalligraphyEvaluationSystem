package com.moyunzhijiao.system_backend.entiy.resource;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value="article")
public class Article {
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;
    String name ;
    @TableField(value="first_type_id")
    Integer firstTypeId;
    @TableField(value="second_type_id")
    Integer secondTypeId;
    String tag;
    @TableField(value="is_recommended")
    boolean isRecommended;
    @TableField(value="picture_url")
    String pictureUrl;
    String importer;
    @TableField(value="created_time")
    String createdTime;
}
