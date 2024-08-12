package com.moyunzhijiao.system_backend.entiy.competition;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Data;

@Data
@TableName(value = "csubmission_image")
public class CSubmissionImage {
    @MppMultiId
    @TableField("submission_id")
    Integer submissionId;
    @MppMultiId
    @TableField("picture_url")
    String pictureUrl;
}
