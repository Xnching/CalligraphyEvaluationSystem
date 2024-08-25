package com.moyunzhijiao.system_backend.service.competition;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.entiy.competition.CSubmissionImage;
import com.moyunzhijiao.system_backend.entiy.competition.CompetitionSubmissions;
import com.moyunzhijiao.system_backend.entiy.competition.FinalRank;
import com.moyunzhijiao.system_backend.mapper.competition.CompetitionSubmissionsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class CompetitionSubmissionsService extends ServiceImpl<CompetitionSubmissionsMapper, CompetitionSubmissions> {
    @Autowired
    CompetitionSubmissionsMapper competitionSubmissionsMapper;
    @Autowired
    CSubmissionImageService cSubmissionImageService;
    @Autowired
    FinalRankService finalRankService;
    /*
    * 根据作品id获取作品信息包括作品图片
    * */
    public CompetitionSubmissions getBySubmission(Integer submissionId) {
        CompetitionSubmissions competitionSubmissions = getById(submissionId);
        FinalRank finalRank = finalRankService.getById(submissionId);
        if(finalRank!=null){
            competitionSubmissions.setLevel(finalRank.getLevel());
            competitionSubmissions.setFinalRank(finalRank.getRk());
        }
        competitionSubmissions.setImageList(cSubmissionImageService.getImages(submissionId));
        return competitionSubmissions;
    }

    /*
    * 获取一个教师所有现在正在进行中的组别的已批改的作品数,即一个教师的已完成份数
    * */
    public Integer getIngFinishReviewedOfTeacher(Integer teacherId){
        Integer total = competitionSubmissionsMapper.getIngFinishReviewedOfTeacher(teacherId);
        return total;
    }

    public Integer getIngFinishReviewedOfTeacherInDivision(Integer id, Integer divisionId) {
        Integer total = competitionSubmissionsMapper.getIngFinishReviewedOfTeacherInDivision(id,divisionId);
        return total;
    }

    /*
    * 获取一个教师评阅一个组别的初级评阅
    * */
    public IPage<CompetitionSubmissions> getInitialOfTeacher(IPage<CompetitionSubmissions> page, Integer teacherId, Integer divisionId) {
        page = competitionSubmissionsMapper.selectInitialOfTeacher(page,divisionId,teacherId);
        for(CompetitionSubmissions competitionSubmissions:page.getRecords()){
            Integer submissionId = competitionSubmissions.getId();
            competitionSubmissions.setImageList(cSubmissionImageService.getImages(submissionId));
        }
        Long total = competitionSubmissionsMapper.countInitialOfTeacher(divisionId,teacherId);
        page.setTotal(total);
        return page;
    }

    /*
     * 为一个组别更新排名,并返回前百分比的作品id
     * */
    @Transactional
    public List<Integer> updateInitialRank(Integer divisionId, BigDecimal percentage) {
        competitionSubmissionsMapper.updateInitialRank(divisionId);
        // 计算需要返回的记录数
        Long totalCount = competitionSubmissionsMapper.selectCount(new QueryWrapper<CompetitionSubmissions>().eq("division_id", divisionId));
        int limit = percentage.multiply(new BigDecimal(totalCount)).setScale(0, RoundingMode.CEILING).intValue();

        // 查询并返回前百分比的 submission_id
        return competitionSubmissionsMapper.getTopPercentageSubmissions(divisionId, limit);
    }
}
