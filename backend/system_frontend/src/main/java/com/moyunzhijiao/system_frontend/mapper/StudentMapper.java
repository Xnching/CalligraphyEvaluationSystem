package com.moyunzhijiao.system_frontend.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moyunzhijiao.system_frontend.controller.dto.StudentDTO;
import com.moyunzhijiao.system_frontend.entity.Student;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface StudentMapper extends BaseMapper<Student> {
    /*
     * 根据班级获取学生
     * */
    @Select("select s.id,s.name,s.gender,k.name as klass,k.year as year,g.name as grade " +
            "   from student s " +
            "       left join klass k on s.klass_id = k.id " +
            "       left join grade g on g.id = s.grade_id " +
            "where s.klass_id = #{klassId} " +
            "   and s.name like CONCAT('%',#{stuInfo},'%') ")
    List<StudentDTO> selectByKlass(Integer klassId, String stuInfo);

    /*
    * 根据教师获取学生
    * */
    @Select({"<script>",
            "select s.id, s.name, s.gender, k.name as klass, k.year as year, g.name as grade ",
            "   from student s ",
            "       left join klass k on s.klass_id = k.id ",
            "       left join grade g on g.id = s.grade_id ",
            "where s.klass_id in ",
            "   <foreach item='id' collection='ids' open='(' separator=',' close=')'>",
            "       #{id}",
            "   </foreach> ",
            "   and s.name like CONCAT('%', #{name}, '%')",
            "</script>"})
    List<StudentDTO> selectAllByTeacher(@Param("ids") List<Integer> klassIdList, @Param("name") String name);

}
