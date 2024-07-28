package com.moyunzhijiao.system_backend.entiy;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value="template_word")
public class TemplateWord {
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;
    String name;
    String author;
    @TableField(value="structure_id")
    Integer structureId;
    @TableField(value="font_id")
    Integer fontId;
    @TableField(value="radical_id")
    Integer radicalId;
    String content;     //图片的url
    String importer;
    @TableField(exist = false)
    String fileName;
    @TableField(value="grade_id")
    Integer gradeId;
    @TableField(value="created_time")
    String createdTime;
}
