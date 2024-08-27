package com.moyunzhijiao.system_backend.mapper.competition;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_backend.entiy.competition.CompetitionSubmissions;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

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

    @Update({
            "SET @rank = 0;",
            "SET @prev_score = NULL;",
            "SET @count = 0;",
            "UPDATE competition_submissions",
            "JOIN (",
            "    SELECT id, initial_score,",
            "           @rank := IF(@prev_score = initial_score, @rank, @rank + @count + 1) AS rank,",
            "           @count := IF(@prev_score = initial_score, @count + 1, 0) AS count,",
            "           @prev_score := initial_score",
            "    FROM competition_submissions",
            "    WHERE division_id = #{divisionId}",
            "    ORDER BY initial_score DESC",
            ") AS ranked",
            "ON competition_submissions.id = ranked.id",
            "SET competition_submissions.initial_rank = ranked.rank;"
    })
    void updateInitialRank(@Param("divisionId") int divisionId);

    @Select({
            "SELECT id FROM competition_submissions",
            "WHERE division_id = #{divisionId}",
            "ORDER BY initial_rank",
            "LIMIT #{limit}"
    })
    List<Integer> getTopPercentageSubmissions(Integer divisionId, int limit);

    @Select("SELECT id,name,system_score,system_evaluation FROM competition_submissions " +
            "WHERE teacher_id IS NULL AND division_id = #{divisionId} " +
            "ORDER BY id " +
            "LIMIT #{limit} " +
            "FOR UPDATE")
    List<CompetitionSubmissions> getSubmissionsToUpdate(@Param("divisionId") Integer divisionId, @Param("limit") Integer limit);
    @Update("<script>" +
            "UPDATE competition_submissions SET teacher_id = #{teacherId} WHERE id IN " +
            "<foreach item='id' collection='ids' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    void updateSubmissions(@Param("ids") List<Integer> ids, @Param("teacherId") Integer teacherId);
}
