package com.moyunzhijiao.system_frontend.entity.template;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Data;

@Data
@TableName(value = "system_template_image")
public class SystemTemplateImage {
    @MppMultiId
    @TableField("system_template_id")
    Integer systemTemplateId;
    @MppMultiId
    @TableField("picture_url")
    String pictureUrl;
}
