package com.moyunzhijiao.system_frontend.entity.homework;

import com.baomidou.mybatisplus.annotation.TableField;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Data;

@Data
public class HomeworkImage {
    @MppMultiId
    @TableField("homework_id")
    Integer homeworkId;
    @MppMultiId
    @TableField("picture_url")
    String pictureUrl;
}
