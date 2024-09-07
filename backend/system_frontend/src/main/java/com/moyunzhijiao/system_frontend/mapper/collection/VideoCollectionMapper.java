package com.moyunzhijiao.system_frontend.mapper.collection;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_frontend.controller.dto.KnowledgeCollectionDTO;
import com.moyunzhijiao.system_frontend.controller.dto.VideoCollectionDTO;
import com.moyunzhijiao.system_frontend.entity.collection.VideoCollection;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface VideoCollectionMapper extends BaseMapper<VideoCollection> {
    @Insert("INSERT INTO video_knowledge (student_id, video_id) VALUES (#{stuId}, #{vId})")
    public boolean insert_ (Integer stuId,Integer vId);

    @Select("SELECT  " +
            "    vk.video_id AS id, " +
            "    v.name AS name " +
            "FROM " +
            "    video_knowledge vk " +
            "JOIN  " +
            "    video v ON vk.video_id = v.id " +
            "WHERE  " +
            "    vk.student_id = #{stuId} ")
    public IPage<VideoCollectionDTO> getVideoCollection(IPage<VideoCollectionDTO> page, @Param("stuId") Integer stuId);

    @Select("SELECT count(*) " +
            "FROM " +
            "    video_knowledge vk " +
            "WHERE  " +
            "    vk.student_id = #{stuId} ")
    public Integer countVideoCollection(Integer stuId);
}
