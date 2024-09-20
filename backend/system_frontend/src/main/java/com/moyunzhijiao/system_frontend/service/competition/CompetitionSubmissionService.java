package com.moyunzhijiao.system_frontend.service.competition;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moyunzhijiao.system_frontend.controller.dto.CompetitionDetailDTO;
import com.moyunzhijiao.system_frontend.entity.competition.CsubmissionImage;
import com.moyunzhijiao.system_frontend.entity.competition.Division;
import com.moyunzhijiao.system_frontend.mapper.Competition.CompetitionSubmissionMapper;
import com.moyunzhijiao.system_frontend.mapper.Competition.CsubmissionImageMapper;
import com.moyunzhijiao.system_frontend.mapper.StudentMapper;
import com.moyunzhijiao.system_frontend.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompetitionSubmissionService {
    @Autowired
    StudentService studentService;

    @Autowired
    CompetitionSubmissionMapper competitionSubmissionMapper;

    @Autowired
    CsubmissionImageMapper csubmissionImageMapper;

    public CompetitionDetailDTO getCompetitionDetail(Integer comId,Integer stuNo){

        Integer stuId=studentService.getStuIdByStuNo(stuNo);
        return getDetail(comId,stuId);
    }

    public CompetitionDetailDTO getOutstandingDetail(Integer submissionId){
        Integer comId=competitionSubmissionMapper.selectById(submissionId).getCompetitionId();
        Integer stuId=competitionSubmissionMapper.selectById(submissionId).getAuthorId();
        return getDetail(comId,stuId);
    }

    public CompetitionDetailDTO getDetail(Integer divId,Integer stuId){
        CompetitionDetailDTO competitionDetailDTO;
        competitionDetailDTO = competitionSubmissionMapper.selectCompetitionDetail(divId,stuId);
        QueryWrapper<CsubmissionImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("submission_id", competitionDetailDTO.getCompetitionSubmissionId())
                .select("submission_id", "picture_url");
        List<CsubmissionImage> csubmissionImages=csubmissionImageMapper.selectList(queryWrapper);
        System.out.println(csubmissionImages);
        List<String> pictureUrls = csubmissionImages.stream()
                .map(image -> image.getPictureUrl())
                .collect(Collectors.toList());
        competitionDetailDTO.setPictureUrl(pictureUrls);
        return competitionDetailDTO;
    }
}
