package com.moyunzhijiao.system_frontend.mapper.Competition;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moyunzhijiao.system_frontend.controller.dto.CompetitionDetailDTO;
import com.moyunzhijiao.system_frontend.entity.competition.CompetitionSubmission;
import org.apache.ibatis.annotations.Select;

public interface CompetitionSubmissionMapper extends BaseMapper<CompetitionSubmission> {
    @Select("SELECT " +
            "            cs.id AS competitionSubmissionId, " +
            "            c.name AS competitionName, " +
            "            d.name AS divisionName, " +
            "            fr.level AS level, " +
            "            cs.system_score AS systemScore, " +
            "            cs.system_evaluation AS systemEvaluation, " +
            "            fr.score AS finalScore " +
            "        FROM competition_submissions cs " +
            "        JOIN competition c ON cs.competition_id = c.id " +
            "        JOIN division d ON cs.division_id = d.id " +
            "        LEFT JOIN final_rank fr ON cs.id = fr.submission_id " +
            "        LEFT JOIN csubmission_image csi ON cs.id = csi.submission_id " +
            "        WHERE cs.author_id = #{stuId} AND cs.competition_id = #{comId} " )
    public CompetitionDetailDTO selectCompetitionDetail(Integer comId,Integer stuId);
}
