package com.moyunzhijiao.system_frontend.service.homework;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.entity.homework.Homework;
import com.moyunzhijiao.system_frontend.mapper.homework.HomeworkMapper;
import com.moyunzhijiao.system_frontend.mapper.homework.TeacherHomeworkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeworkService extends ServiceImpl<HomeworkMapper, Homework> {

    @Autowired
    TeacherHomeworkMapper teacherHomeworkMapper;
    @Autowired
    HomeworkMapper homeworkMapper;

}
