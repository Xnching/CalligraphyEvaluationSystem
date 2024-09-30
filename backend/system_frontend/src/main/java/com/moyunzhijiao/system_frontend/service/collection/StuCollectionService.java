package com.moyunzhijiao.system_frontend.service.collection;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyunzhijiao.system_frontend.controller.dto.HomeworkCollectionDTO;
import com.moyunzhijiao.system_frontend.controller.dto.KnowledgeCollectionDTO;
import com.moyunzhijiao.system_frontend.controller.dto.VideoCollectionDTO;
import com.moyunzhijiao.system_frontend.entity.collection.HomeworkCollection;
import com.moyunzhijiao.system_frontend.entity.collection.KnowledgeCollection;
import com.moyunzhijiao.system_frontend.entity.collection.VideoCollection;
import com.moyunzhijiao.system_frontend.mapper.collection.HomeworkCollectionMapper;
import com.moyunzhijiao.system_frontend.mapper.collection.KnowledgeCollectionMapper;
import com.moyunzhijiao.system_frontend.mapper.collection.VideoCollectionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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



    public IPage<HomeworkCollectionDTO> getHomeworkCollection(Integer stuId,Integer currentPage,Integer pageSize){
        IPage<HomeworkCollectionDTO> page=new Page<HomeworkCollectionDTO>(currentPage,pageSize);
        int total;
        List<HomeworkCollectionDTO> combinedList=Stream.concat(homeworkCollectionMapper.getHomeworkCollection(page,stuId).getRecords().stream(), homeworkCollectionMapper.getHomeworkCollection_(page,stuId).getRecords().stream())
                .collect(Collectors.toList());
        page.setRecords(combinedList);
        total= homeworkCollectionMapper.countHomeworkCollection(stuId) + homeworkCollectionMapper.countHomeworkCollection_(stuId);
        page.setTotal(total);
        return page;
    }

    public IPage<KnowledgeCollectionDTO> getKnowledgeCollection(Integer stuId, Integer currentPage, Integer pageSize){
        IPage<KnowledgeCollectionDTO> page=new Page<KnowledgeCollectionDTO>(currentPage,pageSize);
        Integer total;
        page= knowledgeCollectionMapper.getKnowledgeCollection(page,stuId);
        total= knowledgeCollectionMapper.countKnowledgeCollection(stuId);
        page.setTotal(total);
        return page;
    }
    public IPage<VideoCollectionDTO> getVideoCollection(Integer stuId, Integer currentPage, Integer pageSize){
        IPage<VideoCollectionDTO> page=new Page<VideoCollectionDTO>(currentPage,pageSize);
        Integer total;
        page= videoCollectionMapper.getVideoCollection(page,stuId);
        total= videoCollectionMapper.countVideoCollection(stuId);
        page.setTotal(total);
        return page;
    }
}
