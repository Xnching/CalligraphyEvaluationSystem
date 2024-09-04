package com.moyunzhijiao.system_backend.mapper.competition;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_backend.controller.dto.front.StudentDTO;
import com.moyunzhijiao.system_backend.entiy.competition.CompetitionSubmissions;
import com.moyunzhijiao.system_backend.entiy.competition.Participant;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ParticipantMapper extends BaseMapper<Participant> {
    //使用动态sql完成查询
    @Select("<script>" +
            "select student.id, student.name, student.student_number, grade.name as grade, submission_id " +
            "from participant " +
            "left join student on participant.student_id = student.id " +
            "left join grade on grade.id = student.grade_id " +
            "where participant.competition_id = #{competitionId} " +
            "and student.name like CONCAT('%', #{str}, '%') " +
            "<if test='gradeId != null'> " +
            "and student.grade_id = #{gradeId} " +
            "</if> " +
            "<if test='status != null'> " +
            "<if test='status == 0'> " +
            "and submission_id = 0 " +
            "</if> " +
            "<if test='status == 1'> " +
            "and submission_id != 0 " +
            "</if> " +
            "</if> " +
            "</script>")
    IPage<StudentDTO> selectStudentByCompetition(IPage<StudentDTO> page, @Param("str") String str, @Param("competitionId")Integer competitionId, @Param("gradeId")Integer gradeId, @Param("status")Integer status);

    @Select("<script>" +
            "select count(*) " +
            "from participant " +
            "   left join student on participant.student_id = student.id " +
            "where participant.competition_id = #{competitionId} " +
            "   and student.name like CONCAT('%', #{str}, '%') " +
            "<if test='gradeId != null'> " +
            "   and student.grade_id = #{gradeId} " +
            "</if> " +
            "<if test='status != null'> " +
            "<if test='status == 0'> " +
            "   and submission_id = 0 " +
            "</if> " +
            "<if test='status == 1'> " +
            "   and submission_id != 0 " +
            "</if> " +
            "</if> " +
            "</script>")
    int countStudentByCompetition(@Param("str") String str, @Param("competitionId") Integer competitionId, @Param("gradeId") Integer gradeId, @Param("status") Integer status);
    @Select("<script>" +
            "SELECT cs.*, " +
            "   fr.level AS level, " +
            "   s.name AS student, " +
            "   GROUP_CONCAT(ci.picture_url) AS imageList " +
            "FROM competition_submissions cs " +
            "   LEFT JOIN participant p ON cs.id = p.submission_id " +
            "   LEFT JOIN final_rank fr ON cs.id = fr.submission_id " +
            "   LEFT JOIN student s ON p.student_id = s.id " +
            "   LEFT JOIN csubmission_image ci ON cs.id = ci.submission_id " +
            "WHERE cs.competition_id = #{competitionId} " +
            "   AND (cs.name LIKE CONCAT('%', #{str}, '%') OR s.name LIKE CONCAT('%', #{str}, '%')) " +
            "<if test='gradeId != null'> " +
            "   AND s.grade_id = #{gradeId} " +
            "</if> " +
            "<if test='level != null'> " +
            "   AND fr.level = #{level} " +
            "</if> " +
            "GROUP BY cs.id, fr.level, s.name " + // Include all non-aggregated columns
            "</script>")
    IPage<CompetitionSubmissions> selectSubmissionByCompetition(IPage<CompetitionSubmissions> page, @Param("str") String str, @Param("competitionId") Integer competitionId, @Param("gradeId") Integer gradeId, @Param("level") String level);


    @Select("<script>" +
            "SELECT COUNT(p.submission_id) " +
            "FROM participant p " +
            "LEFT JOIN competition_submissions cs ON cs.id = p.submission_id " +
            "LEFT JOIN final_rank fr ON cs.id = fr.submission_id " +
            "LEFT JOIN student s ON p.student_id = s.id " +
            "WHERE cs.competition_id = #{competitionId} " +
            "AND (cs.name LIKE CONCAT('%', #{str}, '%') OR s.name LIKE CONCAT('%', #{str}, '%')) " +
            "<if test='gradeId != null'> " +
            "AND s.grade_id = #{gradeId} " +
            "</if> " +
            "<if test='level != null'> " +
            "AND fr.level = #{level} " +
            "</if> " +
            "</script>")
    int countSubmissionByCompetition(@Param("str")String str, @Param("competitionId")Integer competitionId, @Param("gradeId")Integer gradeId, @Param("level")String level);
}
