package com.moyunzhijiao.system_backend.entiy.word;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName(value = "custom_template")
public class CustomTemplate {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String type;
    private String detailType;
    private Integer fontId;
    private Integer creatorId;
    private String creatorType;
    private String difficulty;
    private Integer wordCount;
    private String createdTime;
    @TableField(exist = false)
    List<String> imageList;
    @TableField(exist = false)
    Integer count;
}
