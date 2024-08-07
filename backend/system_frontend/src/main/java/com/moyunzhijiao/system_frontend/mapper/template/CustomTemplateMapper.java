package com.moyunzhijiao.system_frontend.mapper.template;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_frontend.entity.template.CustomTemplate;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface CustomTemplateMapper extends BaseMapper<CustomTemplate> {
    @Select("SELECT " +
            "    ct.* " +
            "    COUNT(th.homework_id) AS usageFrequency " +
            "FROM  " +
            "    custom_template ct " +
            "INNER JOIN  " +
            "    teacher_homework th ON ct.id = th.template_id " +
            "WHERE  " +
            "    th.teacher_id = #{teacherId} AND th.template_type = '自定义' " +
            "GROUP BY  " +
            "    ct.id ")
    IPage<CustomTemplate> selectByTeacher(IPage<CustomTemplate> page, @Param("teacherId") Integer teacherId);

    @Select("SELECT " +
            "    count(*) " +
            "FROM  " +
            "    custom_template ct " +
            "INNER JOIN  " +
            "    teacher_homework th ON ct.id = th.template_id " +
            "WHERE  " +
            "    th.teacher_id = #{teacherId} AND th.template_type = '自定义' " +
            "GROUP BY  " +
            "    ct.* ")
    Integer countByTeacher(@Param("teacherId") Integer teacherId);
}
