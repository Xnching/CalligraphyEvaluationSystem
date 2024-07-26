package com.moyunzhijiao.system_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_backend.controller.dto.KlassDTO;
import com.moyunzhijiao.system_backend.controller.dto.UserDTO;
import com.moyunzhijiao.system_backend.entiy.Klass;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

public interface KlassMapper extends BaseMapper<Klass> {
    @Select("select klass.id,klass.name ,teacher.name as teacher ,grade.name as grade,student_count as count " +
            "from klass left join teacher on klass.teacher_id = teacher.id " +
            "left join grade on klass.grade_id = grade.id " +
            "where klass.delete_flag = 0 and klass.school_id = #{schoolId} and ( " +
            "klass.name LIKE CONCAT('%',#{str},'%') " +
            "or teacher.name LIKE CONCAT('%',#{str},'%') " +
            "or grade.name LIKE CONCAT('%',#{str},'%'))")
    IPage<KlassDTO> selectPage(IPage<KlassDTO> page, @Param("schoolId") Integer schoolId ,@Param("str") String str);


    @Select("SELECT COUNT(*) " +
            "from klass left join teacher on klass.teacher_id = teacher.id " +
            "left join grade on klass.grade_id = grade.id " +
            "where klass.delete_flag = 0 and klass.school_id = #{schoolId} and " +
            "( " +
            "klass.name LIKE CONCAT('%',#{str},'%') " +
            "or teacher.name LIKE CONCAT('%',#{str},'%') " +
            "or grade.name LIKE CONCAT('%',#{str},'%'))")
    Integer countPage(@Param("schoolId") Integer schoolId ,@Param("str") String str);
}
