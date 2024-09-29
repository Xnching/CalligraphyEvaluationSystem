package com.moyunzhijiao.system_frontend.service.homework;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.controller.dto.KlassDetailDTO;
import com.moyunzhijiao.system_frontend.controller.dto.homework.HomeworkSubmissionDTO;
import com.moyunzhijiao.system_frontend.controller.dto.KlassDTO;
import com.moyunzhijiao.system_frontend.controller.dto.StudentDTO;
import com.moyunzhijiao.system_frontend.entity.Student;
import com.moyunzhijiao.system_frontend.entity.homework.Homework;
import com.moyunzhijiao.system_frontend.entity.homework.HomeworkSubmission;
import com.moyunzhijiao.system_frontend.mapper.KlassMapper;
import com.moyunzhijiao.system_frontend.mapper.StudentMapper;
import com.moyunzhijiao.system_frontend.mapper.homework.HomeworkSubmissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HomeworkSubmissionService extends ServiceImpl<HomeworkSubmissionMapper, HomeworkSubmission> {
    @Autowired
    HomeworkSubmissionMapper homeworkSubmissionMapper;
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    KlassMapper klassMapper;

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
        return countLong.intValue();
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
    public IPage<StudentDTO> getStudentPage(Integer homeworkId, IPage<StudentDTO> page){
        page = homeworkSubmissionMapper.selectStudentPage(page,homeworkId);
        page.getRecords().forEach(student -> {
            Integer studentId = student.getStuId();
            // 根据studentId查询klassId和studentNumber (你需要根据你的数据库结构调整查询)
            Student studentInfo = studentMapper.selectById(studentId);
            if (studentInfo != null) {
                Integer klassId = studentInfo.getKlassId();
                String studentNumber = studentInfo.getStudentNumber(); // 假设studentNumber存储在student表中
                // 根据klassId查询klassName
                if (klassId != null) {
                    KlassDTO klassDTO = klassMapper.getKlassById(klassId);
                    if (klassDTO != null) {
                        student.setKlass(klassDTO.getName());
                    }
                }
                // 设置studentNumber
                student.setStuno(studentNumber);
            }
        });

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
        List<HomeworkSubmission> homeworkSubmissionList = list.stream().map(studentId->{
            HomeworkSubmission homeworkSubmission = new HomeworkSubmission();
            homeworkSubmission.setHomeworkId(homeworkId);
            homeworkSubmission.setStudentId(studentId);
            homeworkSubmission.setState(false);
            homeworkSubmission.setReviewed(0);
            return homeworkSubmission;
        }).toList();
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

    /*
    * 获取一个作业的所有,包括作业名称，包括未被批改的学生名、被批改过的学生名
    * */
    public Map<String,?> getSubmissionListOfHomework(Homework homework,Integer klass) {
        Map<String,Object> map = new HashMap<>();
        map.put("name",homework.getName());
        List<StudentDTO> uncorrectedList = homeworkSubmissionMapper.selectStudentOfHomework(homework.getId(),klass,false);
        map.put("unCorrected",uncorrectedList);
        List<StudentDTO> correctedList = homeworkSubmissionMapper.selectStudentOfHomework(homework.getId(),klass,true);
        map.put("corrected",correctedList);
        return map;
    }

    /*
    * 评阅作品
    * */
    public void reviewSubmission(Integer submissionId, Integer score, String feedback) {
        HomeworkSubmission homeworkSubmission = getById(submissionId);
        homeworkSubmission.setReviewed(2);
        homeworkSubmission.setTeacherFeedback(feedback);
        homeworkSubmission.setTeacherScore(score);
        updateById(homeworkSubmission);
    }

    @Transactional
    public void deleteByHomework(Integer homeworkId) {
        QueryWrapper<HomeworkSubmission> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("homework_id",homeworkId);
        homeworkSubmissionMapper.delete(queryWrapper);
    }
}
