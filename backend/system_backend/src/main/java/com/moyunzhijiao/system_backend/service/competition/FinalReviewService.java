package com.moyunzhijiao.system_backend.service.competition;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.entiy.competition.CompetitionSubmissions;
import com.moyunzhijiao.system_backend.entiy.competition.FinalReview;
import com.moyunzhijiao.system_backend.mapper.competition.FinalReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinalReviewService extends ServiceImpl<FinalReviewMapper, FinalReview> {
    @Autowired
    FinalReviewMapper finalReviewMapper;
    @Autowired
    CSubmissionImageService cSubmissionImageService;
    /*
    * 一个教师评阅该组别的最终评分
    * */
    public IPage<CompetitionSubmissions> getFinalOfTeacher(IPage<CompetitionSubmissions> page, Integer teacherId, Integer divisionId) {
        page = finalReviewMapper.selectFinalOfTeacher(page,teacherId,divisionId);
        Long total = finalReviewMapper.countFinalOfTeacher(teacherId,divisionId);
        for(CompetitionSubmissions competitionSubmissions:page.getRecords()){
            Integer submissionId = competitionSubmissions.getId();
            competitionSubmissions.setImageList(cSubmissionImageService.getImages(submissionId));
        }
        page.setTotal(total);
        return page;
    }
}
