package com.moyunzhijiao.system_frontend.service.homework;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.entity.homework.Homework;
import com.moyunzhijiao.system_frontend.entity.homework.KlassHomework;
import com.moyunzhijiao.system_frontend.mapper.homework.KlassHomeworkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KlassHomeworkService extends ServiceImpl<KlassHomeworkMapper, KlassHomework> {
    @Autowired
    KlassHomeworkMapper klassHomeworkMapper;

    @Autowired
    HomeworkSubmissionService homeworkSubmissionService;

    /*
    * 获取已截止的班级作业
    * */
    public List<Homework> getDeadlineHomeworkByKlass(Integer classId) {
        List<Homework> list = klassHomeworkMapper.getDeadlineHomework(classId);
        return list;
    }

    /*
    * 根据作业获取所有需完成此次作业的班级，用于查找一个集体作业下面的班级列表
    * */
    public List<Integer> getKlassIdByHomework(Integer homeworkId){
        QueryWrapper<KlassHomework>queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("homework_id",homeworkId);
        queryWrapper.select("klass_id");
        List<KlassHomework> klassHomeworkList = klassHomeworkMapper.selectList(queryWrapper);
        //手动从查找出来的结果中剥离出klassId
        List<Integer> klassIds = new ArrayList<>();
        for (KlassHomework klassHomework : klassHomeworkList) {
            klassIds.add(klassHomework.getKlassId());
        }
        return klassIds;
    }
}
