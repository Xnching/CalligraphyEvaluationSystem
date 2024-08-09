package com.moyunzhijiao.system_frontend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "copybook")
public class Copybook {
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;
    String name;
    String author;
    @TableField("font_id")
    Integer fontId;
    String font;
    String importer;
    String content;
    @TableField("grade_id")
    Integer gradeId;
    String grade;
    @TableField("created_time")
    String createdTime;
}
