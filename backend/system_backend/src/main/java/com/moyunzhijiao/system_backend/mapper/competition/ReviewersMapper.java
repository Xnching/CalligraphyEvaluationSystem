package com.moyunzhijiao.system_backend.mapper.competition;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_backend.controller.dto.front.TeacherDTO;
import com.moyunzhijiao.system_backend.entiy.competition.Division;
import com.moyunzhijiao.system_backend.entiy.competition.Reviewers;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ReviewersMapper extends BaseMapper<Reviewers> {
    @Select(
            "SELECT t.id,t.name,school.name as school " +
            "   FROM teacher t " +
            "   JOIN reviewers r ON t.id = r.teacher_id " +
            "   JOIN division d ON r.division_id = d.id " +
            "   JOIN competition c ON d.competition_id = c.id " +
            "   join school on t.school_id = school.id " +
            "WHERE c.state NOT IN ('准备报名中', '已结束') " +
            "   AND t.delete_flag = 0 " +
            "   AND c.delete_flag = 0 " +
            "   and (t.name like CONCAT('%',#{str},'%') " +
            "   or school.name like CONCAT('%',#{str},'%'))" +
            "GROUP BY t.id " )
    IPage<TeacherDTO> selectIngTeacher(IPage<TeacherDTO> page, @Param("str")String str);

    @Select(
            "SELECT COUNT(distinct r.teacher_id) " +
            "   FROM reviewers r " +
            "   JOIN teacher t ON t.id = r.teacher_id " +
            "   JOIN division d ON r.division_id = d.id " +
            "   JOIN competition c ON d.competition_id = c.id " +
            "   join school on t.school_id = school.id " +
            "WHERE c.state NOT IN ('准备报名中', '已结束') " +
            "   AND t.delete_flag = 0 " +
            "   AND c.delete_flag = 0 " +
            "   and (t.name like CONCAT('%',#{str},'%') " +
            "       or school.name like CONCAT('%',#{str},'%'))" )
    Long countIngTeacher(@Param("str")String str);

    @Select(
            "SELECT COUNT(distinct r.teacher_id) " +
            "   FROM reviewers r " +
            "   JOIN teacher t ON t.id = r.teacher_id " +
            "   JOIN division d ON r.division_id = d.id " +
            "   JOIN competition c ON d.competition_id = c.id " +
            "   join school on t.school_id = school.id " +
            "WHERE r.division_id = #{divisionId} " +
            "   AND c.state NOT IN ('准备报名中', '已结束') " +
            "   AND t.delete_flag = 0 " +
            "   AND c.delete_flag = 0 " +
            "   and (t.name like CONCAT('%',#{str},'%') " +
            "       or school.name like CONCAT('%',#{str},'%'))" )
    Long countIngTeacherInDivision(@Param("str")String str, @Param("divisionId") Integer divisionId);

    @Select(
            "SELECT t.id,t.name,school.name as school " +
            "   FROM teacher t " +
            "   JOIN reviewers r ON t.id = r.teacher_id " +
            "   JOIN division d ON r.division_id = d.id " +
            "   JOIN competition c ON d.competition_id = c.id " +
            "   join school on t.school_id = school.id " +
            "WHERE r.division_id = #{divisionId} " +
            "   AND c.state NOT IN ('准备报名中', '已结束') " +
            "   AND t.delete_flag = 0 " +
            "   AND c.delete_flag = 0 " +
            "   and (t.name like CONCAT('%',#{str},'%') " +
            "       or school.name like CONCAT('%',#{str},'%'))" )
    IPage<TeacherDTO> selectIngTeacherInDivision(IPage<TeacherDTO> page, @Param("str")String str, @Param("divisionId")Integer divisionId);

    @Update({
            "<script>",
            "UPDATE reviewers",
            "   SET is_urged = 1",
            "WHERE (division_id, teacher_id) IN",
            "<foreach collection='list' item='item' open='(' separator=',' close=')'>",
            "   (#{item.divisionId}, #{item.teacherId})",
            "</foreach>",
            "</script>"
    })
    void setUrged(@Param("list") List<Reviewers> reviewersList);

    @Select("select division.id,division.name " +
            "   from reviewers " +
            "   join division on reviewers.division_id = division.id " +
            "where reviewers.teacher_id = #{teacherId} ")
    List<Division> getDivisionOfTeacher(Integer teacherId);
}
