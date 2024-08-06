package com.moyunzhijiao.system_frontend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@TableName(value="homework")
public class Homework {
    @TableId(value="id",type = IdType.AUTO)
    Integer id;
    String name;
    String type;
    @TableField("detail_type")
    String detailType;
    @TableField("font_id")
    Integer fontId;
    @TableField("word_count")
    Integer wordCount;
    BigDecimal score;
    String requirements;
    String target;
    Timestamp deadline;
    Integer difficulty;
    @TableField("created_time")
    String createdTime;
}