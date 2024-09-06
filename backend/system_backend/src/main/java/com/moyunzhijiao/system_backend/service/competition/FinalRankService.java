package com.moyunzhijiao.system_backend.service.competition;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.entiy.competition.CompetitionSubmissions;
import com.moyunzhijiao.system_backend.entiy.competition.Division;
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
    public void updateScoreAndRanks(Division division) {
        Integer divisionId = division.getId();
        Float specialPrizeRatio = division.getSpecialPrizeRatio();
        Float firstPrizeRatio = division.getFirstPrizeRatio();
        Float secondPrizeRatio = division.getSecondPrizeRatio();
        Float thirdPrizeRatio = division.getThirdPrizeRatio();
        finalRankMapper.updateScore(divisionId);
        finalRankMapper.updateRanks(divisionId);

        // 获取该组别的总作品数
        QueryWrapper<FinalRank> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("division_id",divisionId);
        Long totalSubmissions = finalRankMapper.selectCount(queryWrapper);
        // 计算各个奖项的排名限制
        int specialPrizeLimit = Math.round(totalSubmissions * specialPrizeRatio);
        int firstPrizeLimit = Math.round(totalSubmissions * firstPrizeRatio) + specialPrizeLimit;
        int secondPrizeLimit = Math.round(totalSubmissions * secondPrizeRatio) + firstPrizeLimit;
        int thirdPrizeLimit = Math.round(totalSubmissions * thirdPrizeRatio) + secondPrizeLimit;

        // 更新等级
        finalRankMapper.updateLevel(divisionId, specialPrizeLimit, firstPrizeLimit, secondPrizeLimit, thirdPrizeLimit);
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
