package com.moyunzhijiao.system_backend.service.competition;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.entiy.competition.CompetitionSubmissions;
import com.moyunzhijiao.system_backend.entiy.competition.FinalRank;
import com.moyunzhijiao.system_backend.mapper.competition.FinalRankMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FinalRankService extends ServiceImpl<FinalRankMapper, FinalRank> {
    @Autowired
    FinalRankMapper finalRankMapper;
    @Autowired
    CSubmissionImageService cSubmissionImageService;

    /*
    * 更新最终评分均分以及排名
    * */
    @Transactional
    public void updateScoreAndRanks(Integer divisionId) {
        finalRankMapper.updateScore(divisionId);
        finalRankMapper.updateRanks(divisionId);
    }

    /*
    * 获取一个竞赛的进入最终评阅的作品
    * */
    public IPage<CompetitionSubmissions> getFinalToReview(IPage<CompetitionSubmissions> page, Integer divisionId) {
        page = finalRankMapper.selectFinalToReview(page,divisionId);
        Long total = finalRankMapper.countFinalToReview(divisionId);
        page.setTotal(total);
        page.getRecords().forEach(competitionSubmissions ->
            competitionSubmissions.setImageList(
                    cSubmissionImageService.getImages(competitionSubmissions.getId())
            )
        );
        return page;
    }
}
