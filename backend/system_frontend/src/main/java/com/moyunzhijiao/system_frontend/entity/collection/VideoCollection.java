package com.moyunzhijiao.system_frontend.entity.collection;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value="video_knowledge")
public class VideoCollection {
    Integer studentId;
    Integer videoId;
}
