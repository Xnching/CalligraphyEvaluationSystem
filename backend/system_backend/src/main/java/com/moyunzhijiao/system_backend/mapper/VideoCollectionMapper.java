package com.moyunzhijiao.system_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moyunzhijiao.system_backend.entiy.Video;
import com.moyunzhijiao.system_backend.entiy.VideoCollection;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface VideoCollectionMapper extends BaseMapper<VideoCollection> {
    @Select("select video.id, video.name " +
            "from video_collection " +
            "left join video on video.id = video_collection.video_id " +
            "where video_collection.collection_id = #{collectionId} " +
            "order by video_collection.sequence ASC")
    List<Video> getVideos(@Param("collectionId")Integer collectionId);


}
