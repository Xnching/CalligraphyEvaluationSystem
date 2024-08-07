package com.moyunzhijiao.system_backend.entiy.resource;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Data;

@Data
@TableName(value="video_collection")
public class VideoCollection {
    @MppMultiId
    @TableField("video_id")
    Integer videoId;
    @MppMultiId
    @TableField("collection_id")
    Integer collectionId;
    Integer sequence;
}
