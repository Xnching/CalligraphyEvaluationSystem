package com.moyunzhijiao.system_frontend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value="feedback_content")
public class FeedbackContent {
    @TableId(value = "id",type = IdType.INPUT)
    Integer feedbackId;
    String message;
    String reply;
    String file;
}
