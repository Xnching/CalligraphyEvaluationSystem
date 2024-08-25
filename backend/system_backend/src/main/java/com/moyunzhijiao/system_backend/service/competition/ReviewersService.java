package com.moyunzhijiao.system_backend.service.competition;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.controller.dto.front.TeacherDTO;
import com.moyunzhijiao.system_backend.entiy.competition.Reviewers;
import com.moyunzhijiao.system_backend.mapper.competition.ReviewersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewersService extends ServiceImpl<ReviewersMapper, Reviewers> {
    @Autowired
    ReviewersMapper reviewersMapper;

    @Autowired
    CompetitionSubmissionsService competitionSubmissionsService;
    /*
    * 查找所有在评阅中的教师
    * */
    public IPage<TeacherDTO> getAllReviewingTeacher(IPage<TeacherDTO> page, String str){
        page = reviewersMapper.selectIngTeacher(page,str);
        for(TeacherDTO teacherDTO:page.getRecords()){
            Integer finishReviewed = competitionSubmissionsService.getIngFinishReviewedOfTeacher(teacherDTO.getId());
            teacherDTO.setFinishReviewed(finishReviewed);
        }
        Long total = reviewersMapper.countIngTeacher(str);
        page.setTotal(total);
        return page;
    }

    @Transactional
    public void urgeTeacherAllDivision(Integer teacherId) {
        QueryWrapper<Reviewers> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id",teacherId);
        List<Reviewers> reviewers = reviewersMapper.selectList(queryWrapper);
        reviewersMapper.setUrged(reviewers);
    }

    @Transactional
    public void urgeDivisionAllTeacher(Integer divisionId) {
        QueryWrapper<Reviewers> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("division_id",divisionId);
        List<Reviewers> reviewers = reviewersMapper.selectList(queryWrapper);
        reviewersMapper.setUrged(reviewers);
    }

    @Transactional
    public void deleteTeacher(Integer id) {
        QueryWrapper<Reviewers> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id",id);
        remove(queryWrapper);
    }

    /*
    * 获取一个组别里的所有评阅教师
    * */
    public IPage<TeacherDTO> getReviewingTeacherInDivision(IPage<TeacherDTO> page, String str, Integer divisionId) {
        page = reviewersMapper.selectIngTeacherInDivision(page,str,divisionId);
        for(TeacherDTO teacherDTO:page.getRecords()){
            Integer finishReviewed = competitionSubmissionsService.getIngFinishReviewedOfTeacherInDivision(teacherDTO.getId(),divisionId);
            teacherDTO.setFinishReviewed(finishReviewed);
        }
        Long total = reviewersMapper.countIngTeacherInDivision(str,divisionId);
        page.setTotal(total);
        return page;
    }

    /*
    * 删除一个教师在一个组里的评阅
    * */
    public void deleteTeacherInDivision(Integer divisionId, Integer teacherId) {
        QueryWrapper<Reviewers> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("division_id",divisionId);
        queryWrapper.eq("teacher_id",teacherId);
        remove(queryWrapper);
    }

    public void urgeTeacherInDivision(Integer teacherId,Integer divisionId) {
        Reviewers reviewers = new Reviewers(teacherId,divisionId);
        List<Reviewers> list = new ArrayList<>();
        list.add(reviewers);
        reviewersMapper.setUrged(list);
    }
}
