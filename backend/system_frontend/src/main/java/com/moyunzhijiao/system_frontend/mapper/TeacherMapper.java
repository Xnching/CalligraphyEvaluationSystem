package com.moyunzhijiao.system_frontend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moyunzhijiao.system_frontend.controller.dto.TeacherDTO;
import com.moyunzhijiao.system_frontend.entity.Teacher;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface TeacherMapper extends BaseMapper<Teacher> {
    @Select("select teacher.*,school.name as school ,school.region_id as regionId " +
            "from teacher left join school on teacher.school_id = school.id " +
            "where teacher.id = #{teacherId} ")
    public TeacherDTO selectInformation(@Param("teacherId")Integer teacherId);
}
