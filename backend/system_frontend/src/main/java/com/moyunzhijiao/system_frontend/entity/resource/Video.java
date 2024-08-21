package com.moyunzhijiao.system_frontend.entity.resource;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "video")
public class Video {
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
    String content;
    String summary;
    String importer;
    String picture_url;
    String created_time;

}
