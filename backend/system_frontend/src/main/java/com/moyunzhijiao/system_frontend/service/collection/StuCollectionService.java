package com.moyunzhijiao.system_frontend.service.collection;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moyunzhijiao.system_frontend.entity.collection.HomeworkCollection;
import com.moyunzhijiao.system_frontend.entity.collection.KnowledgeCollection;
import com.moyunzhijiao.system_frontend.entity.collection.VideoCollection;
import com.moyunzhijiao.system_frontend.mapper.collection.HomeworkCollectionMapper;
import com.moyunzhijiao.system_frontend.mapper.collection.KnowledgeCollectionMapper;
import com.moyunzhijiao.system_frontend.mapper.collection.VideoCollectionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class StuCollectionService {
    @Autowired
    KnowledgeCollectionMapper knowledgeCollectionMapper;

    @Autowired
    VideoCollectionMapper videoCollectionMapper;

    @Autowired
    HomeworkCollectionMapper homeworkCollectionMapper;

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

    public Boolean addVideoCollection(Integer stuId,Integer vId){
        if(videoCollectionMapper.insert_(stuId,vId)){
            return true;
        }else{
            return false;
        }
    }

    public Boolean delVideoCollection(Integer stuId,Integer kId){
        QueryWrapper<VideoCollection>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("student_id",stuId);
        queryWrapper.eq("video_id",kId);
        if(videoCollectionMapper.delete(queryWrapper)==1){
            return true;
        }else{
            return false;
        }
    }

    public Boolean queryVideoCollection(Integer stuId,Integer vId){
        QueryWrapper<VideoCollection>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("student_id",stuId);
        queryWrapper.eq("video_id",vId);
        System.out.println(videoCollectionMapper.selectOne(queryWrapper));
        if(Objects.nonNull(videoCollectionMapper.selectOne(queryWrapper))){
            return true;
        }else{
            return false;
        }
    }

    public Boolean addHomeworkCollection(Integer stuId,Integer vId,String type){
        if(homeworkCollectionMapper.insert_(stuId,vId,type)){
            return true;
        }else{
            return false;
        }
    }

    public Boolean delHomeworkCollection(Integer stuId,Integer hId){
        QueryWrapper<HomeworkCollection>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("student_id",stuId);
        queryWrapper.eq("submission_id",hId);
        if(homeworkCollectionMapper.delete(queryWrapper)==1){
            return true;
        }else{
            return false;
        }
    }

    public Boolean queryHomeworkCollection(Integer stuId,Integer hId,String type){
        QueryWrapper<HomeworkCollection>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("student_id",stuId);
        queryWrapper.eq("submission_id",hId);
        queryWrapper.eq("type",type);
        System.out.println(homeworkCollectionMapper.selectOne(queryWrapper));
        if(Objects.nonNull(homeworkCollectionMapper.selectOne(queryWrapper))){
            return true;
        }else{
            return false;
        }
    }
}
