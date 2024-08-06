package com.moyunzhijiao.system_frontend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.entity.Homework;
import com.moyunzhijiao.system_frontend.entity.HomeworkSubmission;
import com.moyunzhijiao.system_frontend.entity.KlassHomework;
import com.moyunzhijiao.system_frontend.mapper.KlassHomeworkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KlassHomeworkService extends ServiceImpl<KlassHomeworkMapper, KlassHomework> {
    @Autowired
    KlassHomeworkMapper klassHomeworkMapper;

    @Autowired
    HomeworkSubmissionService homeworkSubmissionService;

    public List<Homework> getDeadlineHomeworkByKlass(Integer classId) {
        List<Homework> list = klassHomeworkMapper.getDeadlineHomework(classId);
        return list;
    }

    public Integer getKlassIdByHomework(Integer homeworkId){
        KlassHomework klassHomework = klassHomeworkMapper.selectById(homeworkId);
        return klassHomework.getKlassId();
    }
}
