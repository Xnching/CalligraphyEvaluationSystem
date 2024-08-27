package com.moyunzhijiao.system_backend.mapper.competition;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_backend.entiy.competition.CompetitionSubmissions;
import com.moyunzhijiao.system_backend.entiy.competition.FinalRank;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface FinalRankMapper extends BaseMapper<FinalRank> {
    @Update({
            "UPDATE final_rank fr ",
            "JOIN (",
            "    SELECT fr.submission_id,AVG(fr.score) AS avg_score ",
            "    FROM final_review fr ",
            "    GROUP BY fr.submission_id ",
            ") AS avg_scores",
            "ON fr.submission_id = avg_scores.submission_id ",
            "SET fr.score = avg_scores.avg_score " +
            "where fr.division_id = #{divisionId};"
    })
    void updateScore(Integer divisionId);

    @Update({
            "SET @rank = 0; ",
            "SET @prev_score = NULL; ",
            "SET @count = 0; ",
            "UPDATE final_rank fr ",
            "JOIN (",
            "    SELECT submission_id, division_id, score, ",
            "           @rank := IF(@prev_score = score, @rank, @rank + @count + 1) AS rank, ",
            "           @count := IF(@prev_score = score, @count + 1, 0) AS count, ",
            "           @prev_score := score ",
            "    FROM final_rank",
            "    WHERE division_id = #{divisionId} ",
            "    ORDER BY score DESC ",
            ") AS ranked ",
            "ON fr.submission_id = ranked.submission_id AND fr.division_id = ranked.division_id ",
            "SET fr.rk = ranked.rank;"
    })
    void updateRanks(Integer divisionId);

    @Select("select cs.id,cs.name " +
            "from final_rank fr " +
            "left join competition_submissions cs on fr.submission_id = cs.id " +
            "where fr.division_id = #{divisionId} ")
    IPage<CompetitionSubmissions> selectFinalToReview(IPage<CompetitionSubmissions> page, Integer divisionId);

    @Select("select count submission_id " +
            "from final_rank " +
            "where division_id = #{divisionId} ")
    Long countFinalToReview(Integer divisionId);
}
