package com.moyunzhijiao.system_frontend.service.homework;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.controller.dto.HomeworkSubmissionDTO;
import com.moyunzhijiao.system_frontend.controller.dto.HomeworkSubmissionDetailDTO;
import com.moyunzhijiao.system_frontend.entity.Student;
import com.moyunzhijiao.system_frontend.entity.homework.Homework;
import com.moyunzhijiao.system_frontend.entity.homework.HomeworkSubmission;
import com.moyunzhijiao.system_frontend.mapper.homework.HomeworkSubmissionMapper;
import com.moyunzhijiao.system_frontend.service.KlassService;
import com.moyunzhijiao.system_frontend.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /*
    * 根据个人作业，查找学生信息
    * */
    public IPage<Student> getStudentPage(Integer homeworkId,IPage<Student> page){
        page = homeworkSubmissionMapper.selectStudentPage(page,homeworkId);
        QueryWrapper<HomeworkSubmission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("homework_id",homeworkId);
        LambdaQueryWrapper<Student> queryWrapper1 = new LambdaQueryWrapper<>();

        Integer total = Math.toIntExact(homeworkSubmissionMapper.selectCount(queryWrapper));
        page.setTotal(total);
        return page;
    }

    /*
    * 根据作业批量布置，list为学生list
    * */
    @Transactional
    public void addByHomework(Integer homeworkId, List<Integer> list) {
        System.out.println("让我看看有没有保存作业");
        List<HomeworkSubmission> homeworkSubmissionList = list.stream().map(studentId->{
            HomeworkSubmission homeworkSubmission = new HomeworkSubmission();
            homeworkSubmission.setHomeworkId(homeworkId);
            homeworkSubmission.setStudentId(studentId);
            homeworkSubmission.setState(false);
            homeworkSubmission.setReviewed(0);
            return homeworkSubmission;
        }).toList();
        //批量保存
        System.out.println("让我看看列表，"+homeworkSubmissionList);
        saveBatch(homeworkSubmissionList);
    }

    /*
    * 根据学生id和作业id找到这个学生的作业作品
    * */
    public HomeworkSubmission getSubmissionByStuAndWork(Integer stuId, Integer homeworkId) {
        QueryWrapper<HomeworkSubmission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id",stuId);
        queryWrapper.eq("homework_id",homeworkId);
        return  homeworkSubmissionMapper.selectOne(queryWrapper);
    }


}
