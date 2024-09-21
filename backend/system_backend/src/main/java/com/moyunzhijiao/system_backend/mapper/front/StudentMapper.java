package com.moyunzhijiao.system_backend.mapper.front;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_backend.controller.dto.front.StudentDTO;
import com.moyunzhijiao.system_backend.entiy.front.Student;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface StudentMapper extends BaseMapper<Student> {
    @Select("select student.id, student.name, grade.name as grade, klass.name as klass " +
            "   from student " +
            "       left join klass on klass.id = student.klass_id " +
            "       left join grade on grade.id = klass.grade_id " +
            "where student.delete_flag = 0 and student.klass_id = #{kid} ")
    IPage<StudentDTO> getStudentsByKlass(IPage<StudentDTO> page,@Param("kid") Integer kid);

    @Select("select count(*) " +
            "   from student " +
            "       left join klass on klass.id = student.klass_id " +
            "       left join grade on grade.id = klass.grade_id " +
            "where student.delete_flag = 0 and student.klass_id = #{kid} ")
    Integer countStudentByKlass(@Param("kid") Integer kid);

    @Select("select student.id, student.name, grade.name as grade, klass.name as klass ,student.phone" +
            ",student.student_number as studentNumber ,student.password ,student.id_number as idNumber " +
            ",klass.id as klassId, grade.id as gradeId ,gender " +
            "   from student " +
            "       left join klass on klass.id = student.klass_id " +
            "       left join grade on grade.id = student.grade_id " +
            "where student.delete_flag = 0 and ( klass.name LIKE CONCAT('%',#{str},'%') " +
            "   or student.name LIKE CONCAT('%',#{str},'%') " +
            "   or grade.name LIKE CONCAT('%',#{str},'%') ) ")
    IPage<StudentDTO> selectPage(IPage<StudentDTO> page, @Param("schoolId") Integer schoolId ,@Param("str") String str);

    @Select("select count(*) " +
            "   from student " +
            "       left join klass on klass.id = student.klass_id " +
            "       left join grade on grade.id = student.grade_id " +
            "where student.delete_flag = 0 and ( klass.name LIKE CONCAT('%',#{str},'%')" +
            "   or student.name LIKE CONCAT('%',#{str},'%')" +
            "   or grade.name LIKE CONCAT('%',#{str},'%') )")
    Integer countPage(@Param("schoolId") Integer schoolId ,@Param("str") String str);


    @Select("select count(id) " +
            "   from ${target} " +
            "where date_format(created_time,'%Y%m%d') = date_format(curdate(),'%Y%m%d')")
    Integer countSingleAddDataOfDay(String target);

    @Select("select count(id) " +
            "   from ${target} " +
            "where date_format(created_time,'%Y%m') = date_format(curdate(),'%Y%m')")
    Integer countSingleAddDataOfMonth(String target);
}
