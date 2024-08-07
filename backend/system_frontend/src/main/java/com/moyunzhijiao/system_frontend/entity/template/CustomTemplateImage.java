package com.moyunzhijiao.system_frontend.entity.template;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Data;

@Data
@TableName(value = "custom_template_image")
public class CustomTemplateImage {
    @MppMultiId
    @TableField("custom_template_id")
    Integer customTemplateId;
    @MppMultiId
    @TableField("picture_url")
    String pictureUrl;
}
