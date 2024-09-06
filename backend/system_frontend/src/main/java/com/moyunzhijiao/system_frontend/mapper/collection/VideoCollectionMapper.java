package com.moyunzhijiao.system_frontend.mapper.collection;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moyunzhijiao.system_frontend.entity.collection.VideoCollection;
import org.apache.ibatis.annotations.Insert;

public interface VideoCollectionMapper extends BaseMapper<VideoCollection> {
    @Insert("INSERT INTO video_knowledge (student_id, video_id) VALUES (#{stuId}, #{vId})")
    public boolean insert_ (Integer stuId,Integer vId);
}
