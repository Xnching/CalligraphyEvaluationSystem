package com.moyunzhijiao.system_frontend.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.common.Constants;
import com.moyunzhijiao.system_frontend.controller.dto.HomeworkDTO;
import com.moyunzhijiao.system_frontend.entity.Homework;
import com.moyunzhijiao.system_frontend.entity.TeacherHomework;
import com.moyunzhijiao.system_frontend.exception.ServiceException;
import com.moyunzhijiao.system_frontend.mapper.TeacherHomeworkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherHomeworkService extends ServiceImpl<TeacherHomeworkMapper, TeacherHomework> {

    @Autowired
    TeacherHomeworkMapper teacherHomeworkMapper;
    @Autowired
    HomeworkSubmissionService homeworkSubmissionService;

    /*
    * 教师的所有作业列表
    * */
    public IPage<HomeworkDTO> getHomeworkPageOfTeacher(IPage<HomeworkDTO> page, Integer type, Integer teacherId, String name) {
        Integer total;
        switch (type){
            case 1://未截止集体
                page = teacherHomeworkMapper.selectNotDeadOfKlass(page,teacherId,name);
                total = teacherHomeworkMapper.countNotDeadOfKlass(page,teacherId,name);
                page.setTotal(total);
                break;
            case 2://未截止个人
                page = teacherHomeworkMapper.selectNotDeadOfStudent(page,teacherId,name);
                total = teacherHomeworkMapper.countNotDeadOfStudent(page,teacherId,name);
                page.setTotal(total);
                break;
            case 3://已截止集体
                page = teacherHomeworkMapper.selectDeadOfKlass(page,teacherId,name);
                total = teacherHomeworkMapper.countDeadOfKlass(page,teacherId,name);
                page.setTotal(total);
                break;
            case 4://已截止个人
                page =teacherHomeworkMapper.selectDeadOfStudent(page,teacherId,name);
                total = teacherHomeworkMapper.countDeadOfStudent(page,teacherId,name);
                page.setTotal(total);
                break;
            default:
                throw new ServiceException(Constants.CODE_401,"type内容错误!");
        }
        // 遍历每个HomeworkDTO对象，并设置unSubmit属性
        for (HomeworkDTO homeworkDTO : page.getRecords()) {
            //根据作业id查找所有没完成作业的
            Integer unSubmitCount =  homeworkSubmissionService.getNumberOfNotSubmit(homeworkDTO.getId());
            homeworkDTO.setUnSubmit(unSubmitCount);
        }
        return page;
    }
}