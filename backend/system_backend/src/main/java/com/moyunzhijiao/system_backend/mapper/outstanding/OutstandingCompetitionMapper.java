package com.moyunzhijiao.system_backend.mapper.outstanding;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_backend.controller.dto.outstanding.OutstandingCompetitionDTO;
import com.moyunzhijiao.system_backend.entiy.outstanding.OutstandingCompetition;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface OutstandingCompetitionMapper extends BaseMapper<OutstandingCompetition> {
    @Select("select outstanding_competition.submissions_id as submissionId,competition.name as competition,division.name as division,student.name as author" +
            ",competition_submissions.average_final_score as averageFinalScore,final_rank.rk as rk,final_rank.level as level " +
            ",competition_submissions.initial_evaluation as initialEvaluation " +
            "   from outstanding_competition left join competition_submissions on outstanding_competition.submissions_id=competition_submissions.id " +
            "   left join competition on competition_submissions.competition_id = competition.id " +
            "   left join division on division.id = competition_submissions.division_id " +
            "   left join student on student.id = competition_submissions.author_id " +
            "   left join final_rank on final_rank.submission_id = outstanding_competition.submissions_id " +
            "where competition.name LIKE CONCAT('%',#{str},'%') " +
            "   or student.name LIKE CONCAT('%',#{str},'%')")
    IPage<OutstandingCompetitionDTO> page(IPage<OutstandingCompetitionDTO> page, @Param("str")String str);
}
