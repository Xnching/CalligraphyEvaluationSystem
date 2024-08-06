package com.moyunzhijiao.system_frontend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moyunzhijiao.system_frontend.controller.dto.TeacherDTO;
import com.moyunzhijiao.system_frontend.entity.Teacher;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TeacherMapper extends BaseMapper<Teacher> {
    @Select("SELECT teacher.*,teacher.school_id as schoolId, school.name AS school, school.region_id AS regionId, GROUP_CONCAT(DISTINCT grade.name) AS grade " +
            "FROM teacher " +
            "LEFT JOIN school ON teacher.school_id = school.id " +
            "LEFT JOIN klass ON klass.teacher_id = teacher.id " +
            "LEFT JOIN grade ON klass.grade_id = grade.id " +
            "WHERE teacher.id = #{teacherId} " +
            "GROUP BY teacher.id, school.name, school.region_id")
    public TeacherDTO selectInformation(@Param("teacherId") Integer teacherId);
}
