package com.moyunzhijiao.system_frontend.service.collection;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moyunzhijiao.system_frontend.entity.collection.KnowledgeCollection;
import com.moyunzhijiao.system_frontend.mapper.collection.KnowledgeCollectionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class StuCollectionService {
    @Autowired
    KnowledgeCollectionMapper knowledgeCollectionMapper;

    public Boolean addKnowledgeCollection(Integer stuId,Integer kId){
        if(knowledgeCollectionMapper.insert_(stuId,kId)){
            return true;
        }else{
            return false;
        }
    }

    public Boolean delKnowledgeCollection(Integer stuId,Integer kId){
        QueryWrapper<KnowledgeCollection>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("student_id",stuId);
        queryWrapper.eq("resources_id",kId);
        if(knowledgeCollectionMapper.delete(queryWrapper)==1){
            return true;
        }else{
            return false;
        }
    }

    public Boolean queryKnowledgeCollection(Integer stuId,Integer kId){
        QueryWrapper<KnowledgeCollection>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("student_id",stuId);
        queryWrapper.eq("resources_id",kId);
        System.out.println(knowledgeCollectionMapper.selectOne(queryWrapper));
        if(Objects.nonNull(knowledgeCollectionMapper.selectOne(queryWrapper))){
            return true;
        }else{
            return false;
        }
    }
}
