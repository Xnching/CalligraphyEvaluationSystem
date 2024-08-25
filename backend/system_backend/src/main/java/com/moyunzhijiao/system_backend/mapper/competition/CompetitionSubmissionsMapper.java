package com.moyunzhijiao.system_backend.mapper.competition;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_backend.entiy.competition.CompetitionSubmissions;
import org.apache.ibatis.annotations.Select;

public interface CompetitionSubmissionsMapper extends BaseMapper<CompetitionSubmissions> {
    @Select("select count(cs.id) " +
            "from competition_submissions cs " +
            "join competition c on cs.competition_id = c.id " +
            "where c.state NOT IN ('准备报名中', '已结束') " +
            "AND cs.teacher_id = #{teacherId} ")
    Integer getIngFinishReviewedOfTeacher(Integer teacherId);

    @Select("select count(cs.id) " +
            "from competition_submissions cs " +
            "join competition c on cs.competition_id = c.id " +
            "where cs.division_id = #{divisionId} " +
            "and c.state NOT IN ('准备报名中', '已结束') " +
            "AND cs.teacher_id = #{id} ")
    Integer getIngFinishReviewedOfTeacherInDivision(Integer id, Integer divisionId);

    @Select("select cs.* " +
            "from competition_submissions cs " +
            "where teacher_id = #{teacherId} " +
            "and division_id = #{divisionId} ")
    IPage<CompetitionSubmissions> selectInitialOfTeacher(IPage<CompetitionSubmissions> page, Integer divisionId, Integer teacherId);

    @Select("select count(cs.id) " +
            "from competition_submissions cs " +
            "where teacher_id = #{teacherId} " +
            "and division_id = #{divisionId} ")
    Long countInitialOfTeacher(Integer divisionId, Integer teacherId);
}
