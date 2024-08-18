package com.moyunzhijiao.system_frontend.entity.homework;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;


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
    @TableField(exist = false)
    String source;      //来源
    @TableField(exist = false)
    BigDecimal score;
    String requirements;
    String target;
    String deadline;
    Integer difficulty;
    @TableField("created_time")
    String createdTime;
}
