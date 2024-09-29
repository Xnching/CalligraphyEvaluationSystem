package com.moyunzhijiao.system_frontend.mapper.homework;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_frontend.controller.dto.homework.HomeworkDTO;
import com.moyunzhijiao.system_frontend.entity.homework.Homework;
import com.moyunzhijiao.system_frontend.entity.homework.TeacherHomework;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface TeacherHomeworkMapper extends BaseMapper<TeacherHomework> {

    @Select({"<script>",
            "select homework.id as id,homework.deadline as deadline,homework.name ,homework.target " ,
                    "   from teacher_homework " ,
                    "       left join homework on teacher_homework.homework_id = homework.id " ,
                    "where teacher_homework.teacher_id = #{teacherId} " ,
                    "<if test='target != null'> ",
                    "   and homework.target = #{target} " ,
                    "</if>",
                    "   and deadline ${f} NOW() " ,
                    "   and homework.name LIKE CONCAT('%',#{name},'%')",
            "</script>"
    })
    IPage<HomeworkDTO> selectHomeworkPageOfKlass(IPage<HomeworkDTO> page, @Param("teacherId")Integer teacherId,@Param("target")String target, @Param("f")String f,@Param("name")String name);


    @Select({"<script>",
            "select  count(*) " ,
            "   from teacher_homework " ,
                    "left join homework on teacher_homework.homework_id = homework.id " ,
            "where teacher_homework.teacher_id = #{teacherId} " ,
            "<if test='target != null'> ",
            "   and homework.target = #{target} " ,
            "</if>",
            "   and deadline ${f} NOW() " ,
            "   and homework.name LIKE CONCAT('%',#{name},'%')",
            "</script>"
    })
    Integer countHomeworkPageOfKlass(@Param("teacherId")Integer teacherId,@Param("target")String target, @Param("f")String f, @Param("name")String name);

    @Select("select homework.*" +
            "   from teacher_homework left join homework on teacher_homework.homework_id = homework.id " +
            "where teacher_homework.teacher_id = #{teacherId}")
    IPage<Homework> selectAllHomeworkPageOfTeacher(IPage<Homework> page, @Param("teacherId")Integer teacherId);

    @Select("select count(*) " +
            "   from teacher_homework " +
            "where teacher_homework.teacher_id = #{teacherId}")
    Integer countAllHomeworkPageOfTeacher(@Param("teacherId")Integer teacherId);
}
