package com.moyunzhijiao.system_frontend.mapper.template;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_frontend.entity.template.SystemTemplate;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface SystemTemplateMapper extends BaseMapper<SystemTemplate> {
    @Select("select system_template.* , count(teacher_homework.homework_id) as usageFrequency " +
            "   from system_template " +
            "   left join teacher_homework on system_template.id = teacher_homework.template_id " +
            "   and teacher_homework.template_type = '系统' and teacher_homework.teacher_id = #{teacherId} " +
            "group by system_template.id ")
    IPage<SystemTemplate> selectByTeacher(IPage<SystemTemplate> page, @Param("teacherId") Integer teacherId);

    @Select("select count(*) " +
            "from system_template")
    Integer countByTeacher();
}
