package com.moyunzhijiao.system_backend.entiy.word;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Data;

@Data
@TableName("system_template_image")
public class SystemTemplateImage {
    @MppMultiId
    private Integer systemTemplateId;
    @MppMultiId
    private String pictureUrl;
}