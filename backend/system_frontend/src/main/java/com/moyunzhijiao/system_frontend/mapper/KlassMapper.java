package com.moyunzhijiao.system_frontend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_frontend.controller.dto.KlassDTO;
import com.moyunzhijiao.system_frontend.entity.Klass;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface KlassMapper extends BaseMapper<Klass> {
    @Select("select klass.id as id ,klass.name as name , grade.name as grade ,grade.id as gradeId ,klass.year as year ,klass.student_count as total " +
            "from klass left join grade on klass.grade_id = grade.id " +
            "where klass.delete_flag = 0 and teacher_id = #{teacherId} " +
            "and klass.school_id = #{schoolId} and (" +
            "grade.name LIKE CONCAT('%',#{grade},'%')" +
            "and klass.name LIKE CONCAT('%',#{klass},'%')" +
            "and year LIKE CONCAT('%',#{year},'%'))")
    public IPage<KlassDTO> selectKlassByTeacherOwn(IPage<KlassDTO> page, @Param("teacherId") Integer teacherId,@Param("schoolId")Integer schoolId,@Param("grade") String grade , @Param("klass") String klass,@Param("year") String year);

    @Select("select count(*) " +
            "from klass left join grade on klass.grade_id = grade.id " +
            "where klass.delete_flag = 0 and teacher_id = #{teacherId} " +
            "and klass.school_id = #{schoolId} and (" +
            " grade.name LIKE CONCAT('%',#{grade},'%')" +
            "and klass.name LIKE CONCAT('%',#{klass},'%')" +
            "and year LIKE CONCAT('%',#{year},'%'))")
    public Integer countKlassByTeacherOwn(@Param("teacherId") Integer teacherId,@Param("schoolId")Integer schoolId,@Param("grade") String grade , @Param("klass") String klass,@Param("year") String year);

    @Select("select klass.id as id ,klass.name as name , grade.name as grade ,grade.id as gradeId ,klass.year as year ,klass.student_count as total " +
            "from klass left join grade on klass.grade_id = grade.id " +
            "where klass.delete_flag = 0 and teacher_id != #{teacherId} " +
            "and klass.school_id = #{schoolId} and (" +
            " grade.name LIKE CONCAT('%',#{grade},'%')" +
            "and klass.name LIKE CONCAT('%',#{klass},'%')" +
            "and year LIKE CONCAT('%',#{year},'%'))")
    public IPage<KlassDTO> selectKlassByTeacherNotOwn(IPage<KlassDTO> page, @Param("teacherId") Integer teacherId,@Param("schoolId")Integer schoolId,@Param("grade") String grade , @Param("klass") String klass,@Param("year") String year);

    @Select("select count(*) " +
            "from klass left join grade on klass.grade_id = grade.id " +
            "where klass.delete_flag = 0 and teacher_id != #{teacherId} " +
            "and klass.school_id = #{schoolId} and (" +
            " grade.name LIKE CONCAT('%',#{grade},'%')" +
            "and klass.name LIKE CONCAT('%',#{klass},'%')" +
            "and year LIKE CONCAT('%',#{year},'%'))")
    public Integer countKlassByTeacherNotOwn(@Param("teacherId") Integer teacherId,@Param("schoolId")Integer schoolId,@Param("grade") String grade , @Param("klass") String klass,@Param("year") String year);
}
