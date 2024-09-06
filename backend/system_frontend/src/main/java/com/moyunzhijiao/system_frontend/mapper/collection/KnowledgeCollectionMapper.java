package com.moyunzhijiao.system_frontend.mapper.collection;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moyunzhijiao.system_frontend.entity.collection.KnowledgeCollection;
import org.apache.ibatis.annotations.Insert;

public interface KnowledgeCollectionMapper extends BaseMapper<KnowledgeCollection> {
    @Insert("INSERT INTO knowledge_collection (student_id, resources_id) VALUES (#{stuId}, #{kId})")
    public boolean insert_ (Integer stuId,Integer kId);
}
