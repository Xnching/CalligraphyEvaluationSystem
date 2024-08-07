package com.moyunzhijiao.system_frontend.entity.template;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName(value = "system_template")
public class SystemTemplate {
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;
    String name ;
    String type ;           //enum('综合','专项','字帖'),
    @TableField("detail_type")
    String detailType ;     //enum ('偏旁','结构','笔画'),
    @TableField("font_id")
    Integer fontId;
    @TableField("creator_id")
    Integer creatorId ;
    Integer difficulty ;
    @TableField("word_count")
    Integer wordCount ;
    @TableField("created_time")
    String createdTime;
    @TableField(exist = false)
    String templateType="系统";
    @TableField(exist = false)
    Integer usageFrequency;
    @TableField(exist = false)
    List<String> imgs;
}
