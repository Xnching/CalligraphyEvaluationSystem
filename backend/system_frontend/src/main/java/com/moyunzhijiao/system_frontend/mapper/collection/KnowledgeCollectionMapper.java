package com.moyunzhijiao.system_frontend.mapper.collection;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_frontend.controller.dto.KnowledgeCollectionDTO;
import com.moyunzhijiao.system_frontend.entity.collection.KnowledgeCollection;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface KnowledgeCollectionMapper extends BaseMapper<KnowledgeCollection> {
    @Insert("INSERT INTO knowledge_collection (student_id, resources_id) VALUES (#{stuId}, #{kId})")
    public boolean insert_ (Integer stuId,Integer kId);

    @Select("SELECT  " +
            "    kc.resources_id AS id, " +
            "    a.name AS name, " +
            "    a.tag AS type " +
            "FROM " +
            "    knowledge_collection kc " +
            "JOIN  " +
            "    article a ON kc.resources_id = a.id " +
            "WHERE  " +
            "    kc.student_id = #{stuId} ")
    public IPage<KnowledgeCollectionDTO> getKnowledgeCollection(IPage<KnowledgeCollectionDTO> page, @Param("stuId") Integer stuId);

    @Select("SELECT count(*) " +
            "FROM " +
            "    knowledge_collection kc " +
            "WHERE  " +
            "    kc.student_id = #{stuId} ")
    public Integer countKnowledgeCollection(Integer stuId);
}
