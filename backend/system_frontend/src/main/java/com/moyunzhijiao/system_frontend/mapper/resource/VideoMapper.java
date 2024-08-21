package com.moyunzhijiao.system_frontend.mapper.resource;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_frontend.controller.dto.ArticleDTO;
import com.moyunzhijiao.system_frontend.controller.dto.VideoDTO;
import com.moyunzhijiao.system_frontend.entity.resource.Video;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface VideoMapper extends BaseMapper<Video> {
    @Select("SELECT id, name, picture_url as pictureURL, created_time as createdTime " +
            "FROM video " +
            "WHERE name LIKE CONCAT('%', #{name}, '%')")
    public IPage<VideoDTO> getVideo(IPage<VideoDTO> page, @Param("name") String name);

    @Select("SELECT COUNT(*) " +
            "FROM video " +
            "WHERE name LIKE CONCAT('%', #{name}, '%')")
    public Integer countVideo(@Param("name") String name);

    @Select("SELECT id, name, picture_url as pictureURL, created_time as createdTime,tag,content " +
            "FROM video " +
            "WHERE id=#{id} ")
    public VideoDTO getVideoDetail(@Param("id")String id);

    @Select("SELECT collection_id FROM video_collection WHERE video_id = #{id}")
    public List<String> selectCollectionIdsByVideoId(@Param("id") String id);

    @Select("SELECT name FROM video WHERE id IN (SELECT video_id FROM video_collection WHERE collection_id = #{collectionId})")
    public List<String> selectVideoNamesByCollectionId(@Param("collectionId") String id);
}
