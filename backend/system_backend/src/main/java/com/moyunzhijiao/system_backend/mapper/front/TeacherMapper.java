package com.moyunzhijiao.system_backend.mapper.front;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_backend.controller.dto.front.TeacherDTO;
import com.moyunzhijiao.system_backend.entiy.front.Teacher;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestParam;

public interface TeacherMapper extends BaseMapper<Teacher> {
    @Select("SELECT teacher.id, teacher.name, school.name AS school " +
            "   FROM teacher " +
            "   LEFT JOIN school ON teacher.school_id = school.id " +
            "WHERE (teacher.name LIKE CONCAT('%', #{str}, '%') " +
            "   OR school.name LIKE CONCAT('%', #{str}, '%')) " +
            "AND teacher.id NOT IN (" +
            "    SELECT t.id " +
            "    FROM teacher t " +
            "    JOIN reviewers r ON t.id = r.teacher_id " +
            "    JOIN division d ON r.division_id = d.id " +
            "    JOIN competition c ON d.competition_id = c.id " +
            "    WHERE c.state NOT IN ('准备报名中', '已结束') " +
            "    AND t.delete_flag = 0 " +
            "    AND c.delete_flag = 0 " +
            "    GROUP BY t.id " +
            ")")
    IPage<TeacherDTO> selectAllTeacher(IPage<TeacherDTO> page, @Param("str") String str);

    @Select("SELECT count(teacher.id) " +
            "   FROM teacher " +
            "   LEFT JOIN school ON teacher.school_id = school.id " +
            "WHERE (teacher.name LIKE CONCAT('%', #{str}, '%') " +
            "   OR school.name LIKE CONCAT('%', #{str}, '%')) " +
            "AND teacher.id NOT IN (" +
            "    SELECT t.id " +
            "    FROM teacher t " +
            "    JOIN reviewers r ON t.id = r.teacher_id " +
            "    JOIN division d ON r.division_id = d.id " +
            "    JOIN competition c ON d.competition_id = c.id " +
            "    WHERE c.state NOT IN ('准备报名中', '已结束') " +
            "    AND t.delete_flag = 0 " +
            "    AND c.delete_flag = 0 " +
            "    GROUP BY t.id " +
            ")")
    Long countAllTeacher(String str);

    @Select("SELECT count(teacher.id) " +
            "   FROM teacher " +
            "   LEFT JOIN school ON teacher.school_id = school.id " +
            "WHERE (teacher.name LIKE CONCAT('%', #{str}, '%') " +
            "   OR school.name LIKE CONCAT('%', #{str}, '%')) " +
            "AND teacher.id NOT IN (" +
            "   SELECT t.id " +
            "   FROM teacher t " +
            "   JOIN reviewers r ON t.id = r.teacher_id " +
            "   JOIN division d ON r.division_id = d.id " +
            "   JOIN competition c ON d.competition_id = c.id " +
            "   WHERE r.division_id = #{divisionId} " +
            "   AND c.state NOT IN ('准备报名中', '已结束') " +
            "   AND t.delete_flag = 0 " +
            "   AND c.delete_flag = 0 " +
            ")")
    Long countAllTeacherInDivision(String str,Integer divisionId);

    @Select("SELECT teacher.id, teacher.name, school.name AS school " +
            "   FROM teacher " +
            "       LEFT JOIN school ON teacher.school_id = school.id " +
            "WHERE (teacher.name LIKE CONCAT('%', #{str}, '%') " +
            "   OR school.name LIKE CONCAT('%', #{str}, '%')) " +
            "AND teacher.id NOT IN (" +
            "   SELECT t.id " +
            "   FROM teacher t " +
            "       JOIN reviewers r ON t.id = r.teacher_id " +
            "       JOIN division d ON r.division_id = d.id " +
            "       JOIN competition c ON d.competition_id = c.id " +
            "   WHERE r.division_id = #{divisionId} " +
            "       AND c.state NOT IN ('准备报名中', '已结束') " +
            "       AND t.delete_flag = 0 " +
            "       AND c.delete_flag = 0 " +
            ")")
    IPage<TeacherDTO> selectAllTeacherInDivision(IPage<TeacherDTO> page, String str,Integer divisionId);
}
