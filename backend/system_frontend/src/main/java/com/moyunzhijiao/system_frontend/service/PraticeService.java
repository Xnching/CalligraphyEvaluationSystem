package com.moyunzhijiao.system_frontend.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyunzhijiao.system_frontend.controller.dto.HomeworkDTO;
import com.moyunzhijiao.system_frontend.controller.dto.PracticeDTO;
import com.moyunzhijiao.system_frontend.mapper.Pratice.PraticeMapper;
import com.moyunzhijiao.system_frontend.mapper.homework.HomeworkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PraticeService {
    @Autowired
    HomeworkMapper homeworkMapper;

    @Autowired
    PraticeMapper praticeMapper;

    public IPage<HomeworkDTO> getSchoolPratice(Integer id,Integer currentPage, Integer pageSize){
        IPage<HomeworkDTO> page =new Page<>(currentPage,pageSize);
        Integer total;
        page= homeworkMapper.getHomeworkFinished(page,id);
        total= homeworkMapper.countHomeworkFinished(id);
        page.setTotal(total);
        return page;
    }

    public IPage<HomeworkDTO> getSchoolPratice_(Integer id,Integer currentPage, Integer pageSize){
        IPage<HomeworkDTO> page =new Page<>(currentPage,pageSize);
        Integer total;
        page= homeworkMapper.getHomeworkUnfinished(page,id);
        total= homeworkMapper.countHomeworkUnfinished(id);
        page.setTotal(total);
        return page;
    }
    public IPage<PracticeDTO> getSelfPratice(Integer id,Integer currentPage, Integer pageSize){
        IPage<PracticeDTO> page =new Page<>(currentPage,pageSize);
        Integer total;
        page= praticeMapper.getPraticeFinished(page,id);
        total= praticeMapper.countPraticeFinished(id);
        page.setTotal(total);
        return page;
    }

    public IPage<PracticeDTO> getSelfPratice_(Integer id,Integer currentPage, Integer pageSize){
        IPage<PracticeDTO> page =new Page<>(currentPage,pageSize);
        Integer total;
        page= praticeMapper.getPraticeUnfinished(page,id);
        total= praticeMapper.countPraticeUnfinished(id);
        page.setTotal(total);
        return page;
    }

}


