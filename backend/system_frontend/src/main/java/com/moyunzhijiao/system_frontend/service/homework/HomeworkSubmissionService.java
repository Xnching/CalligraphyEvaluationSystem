package com.moyunzhijiao.system_frontend.service.homework;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.controller.dto.HomeworkSubmissionDTO;
import com.moyunzhijiao.system_frontend.entity.homework.HomeworkSubmission;
import com.moyunzhijiao.system_frontend.mapper.homework.HomeworkSubmissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomeworkSubmissionService extends ServiceImpl<HomeworkSubmissionMapper, HomeworkSubmission> {
    @Autowired
    HomeworkSubmissionMapper homeworkSubmissionMapper;

    public IPage<HomeworkSubmissionDTO> getUnSubmitedPage(Integer stuId, Integer currentPage, Integer pageSize) {
        IPage<HomeworkSubmissionDTO> homeworkIPage = new Page<>(currentPage,pageSize);
        homeworkIPage = homeworkSubmissionMapper.selectUnSubmitedPage(homeworkIPage,stuId);
        Integer total = homeworkSubmissionMapper.countUnSubmitedPage(stuId);
        homeworkIPage.setTotal(total);
        return homeworkIPage;
    }


    public IPage<HomeworkSubmissionDTO> getSubmitedPage(Integer stuId, Integer currentPage, Integer pageSize) {
        IPage<HomeworkSubmissionDTO> iPage = new Page<>(currentPage,pageSize);
        iPage = homeworkSubmissionMapper.selectSubmitedPage(iPage,stuId);
        Integer total = homeworkSubmissionMapper.countSubmitedPage(stuId);
        iPage.setTotal(total);
        return iPage;
    }

    /*
    * 根据作业id查找有多少个没交
    * */
    public Integer getNumberOfNotSubmit(Integer homeworkId){
        QueryWrapper<HomeworkSubmission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("homework_id",homeworkId);
        queryWrapper.eq("state",0);
        Long countLong = homeworkSubmissionMapper.selectCount(queryWrapper);
        Integer count = countLong.intValue();
        return count;
    }
    /*
    * 根据作业id批量查找学生id
    * */
    public List<Integer> getStudentIdByHomework(Integer homeworkId){
        QueryWrapper<HomeworkSubmission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("homework_id",homeworkId);
        queryWrapper.select("student_id");
        return this.baseMapper.selectObjs(queryWrapper).stream()
                .map(obj -> (Integer) obj)
                .collect(Collectors.toList());
    }
}
