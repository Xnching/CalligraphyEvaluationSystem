package com.moyunzhijiao.system_frontend.mapper.homework;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_frontend.controller.dto.homework.HomeworkDTO;
import com.moyunzhijiao.system_frontend.entity.homework.Homework;
import com.moyunzhijiao.system_frontend.entity.homework.TeacherHomework;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface TeacherHomeworkMapper extends BaseMapper<TeacherHomework> {

    @Select("select homework.id as id,homework.deadline as deadline,homework.name " +
            "   from teacher_homework left join homework on teacher_homework.homework_id = homework.id " +
            "where teacher_homework.teacher_id = #{teacherId} and homework.target = '集体' and deadline > NOW() " +
            "   and homework.name LIKE CONCAT('%',#{name},'%')")
    IPage<HomeworkDTO> selectNotDeadOfKlass(IPage<HomeworkDTO> page, @Param("teacherId")Integer teacherId, @Param("name")String name);

    @Select("select homework.id as id,homework.deadline as deadline,homework.name " +
            "   from teacher_homework left join homework on teacher_homework.homework_id = homework.id " +
            "where teacher_homework.teacher_id = #{teacherId} and homework.target = '个人' and deadline > NOW() " +
            "   and homework.name LIKE CONCAT('%',#{name},'%')")
    IPage<HomeworkDTO> selectNotDeadOfStudent(IPage<HomeworkDTO> page, @Param("teacherId")Integer teacherId, @Param("name")String name);

    @Select("select homework.id as id,homework.deadline as deadline,homework.name " +
            "   from teacher_homework left join homework on teacher_homework.homework_id = homework.id " +
            "where teacher_homework.teacher_id = #{teacherId} and homework.target = '集体' and deadline < NOW() " +
            "   and homework.name LIKE CONCAT('%',#{name},'%')")
    IPage<HomeworkDTO> selectDeadOfKlass(IPage<HomeworkDTO> page, @Param("teacherId")Integer teacherId, @Param("name")String name);

    @Select("select homework.id as id,homework.deadline as deadline,homework.name " +
            "   from teacher_homework left join homework on teacher_homework.homework_id = homework.id " +
            "where teacher_homework.teacher_id = #{teacherId} and homework.target = '个人' and deadline < NOW() " +
            "   and homework.name LIKE CONCAT('%',#{name},'%')")
    IPage<HomeworkDTO> selectDeadOfStudent(IPage<HomeworkDTO> page,@Param("teacherId") Integer teacherId, @Param("name")String name);

    @Select("select  count(*) " +
            "   from teacher_homework left join homework on teacher_homework.homework_id = homework.id " +
            "where teacher_homework.teacher_id = #{teacherId} and homework.target = '集体' and deadline > NOW() " +
            "   and homework.name LIKE CONCAT('%',#{name},'%')")
    Integer countNotDeadOfKlass(IPage<HomeworkDTO> page, @Param("teacherId")Integer teacherId, @Param("name")String name);

    @Select("select  count(*) " +
            "   from teacher_homework left join homework on teacher_homework.homework_id = homework.id " +
            "where teacher_homework.teacher_id = #{teacherId} and homework.target = '个人' and deadline > NOW() " +
            "   and homework.name LIKE CONCAT('%',#{name},'%')")
    Integer countNotDeadOfStudent(IPage<HomeworkDTO> page,@Param("teacherId") Integer teacherId, @Param("name")String name);

    @Select("select  count(*) " +
            "   from teacher_homework left join homework on teacher_homework.homework_id = homework.id " +
            "where teacher_homework.teacher_id = #{teacherId} and homework.target = '集体' and deadline < NOW() " +
            "   and homework.name LIKE CONCAT('%',#{name},'%')")
    Integer countDeadOfKlass(IPage<HomeworkDTO> page,@Param("teacherId") Integer teacherId, @Param("name")String name);

    @Select("select count(*) " +
            "   from teacher_homework left join homework on teacher_homework.homework_id = homework.id " +
            "where teacher_homework.teacher_id = #{teacherId} and homework.target = '个人' and deadline < NOW() " +
            "   and homework.name LIKE CONCAT('%',#{name},'%')")
    Integer countDeadOfStudent(IPage<HomeworkDTO> page,@Param("teacherId") Integer teacherId, @Param("name")String name);

    @Select("select homework.*" +
            "   from teacher_homework left join homework on teacher_homework.homework_id = homework.id " +
            "where teacher_homework.teacher_id = #{teacherId}")
    IPage<Homework> selectAllHomeworkPageOfTeacher(IPage<Homework> page, @Param("teacherId")Integer teacherId);

    @Select("select count(*) " +
            "   from teacher_homework " +
            "where teacher_homework.teacher_id = #{teacherId}")
    Integer countAllHomeworkPageOfTeacher(@Param("teacherId")Integer teacherId);
}
