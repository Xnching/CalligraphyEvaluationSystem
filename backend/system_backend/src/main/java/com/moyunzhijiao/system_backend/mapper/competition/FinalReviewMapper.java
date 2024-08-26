package com.moyunzhijiao.system_backend.mapper.competition;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_backend.entiy.competition.CompetitionSubmissions;
import com.moyunzhijiao.system_backend.entiy.competition.FinalReview;
import org.apache.ibatis.annotations.Select;

public interface FinalReviewMapper extends BaseMapper<FinalReview> {
    @Select("select cs.*,fr.score as finalScore " +
            "from final_review fr " +
            "join competition_submissions cs on fr.submission_id = cs.id " +
            "where fr.teacher_id = #{teacherId} " +
            "and cs.division_id = #{divisionId} ")
    IPage<CompetitionSubmissions> selectFinalOfTeacher(IPage<CompetitionSubmissions> page, Integer teacherId, Integer divisionId);

    @Select("select count(cs.id) " +
            "from final_review fr " +
            "join competition_submissions cs on fr.submission_id = cs.id " +
            "where fr.teacher_id = #{teacherId} " +
            "and cs.division_id = #{divisionId} ")
    Long countFinalOfTeacher(Integer teacherId, Integer divisionId);
}
