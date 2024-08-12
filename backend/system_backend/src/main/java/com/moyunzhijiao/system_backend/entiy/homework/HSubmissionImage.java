package com.moyunzhijiao.system_backend.entiy.homework;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Data;

@Data
@TableName(value = "hsubmission_image")
public class HSubmissionImage {
    @MppMultiId
    @TableField("submission_id")
    Integer submissionId;
    @MppMultiId
    @TableField("picture_url")
    String pictureUrl;
}
