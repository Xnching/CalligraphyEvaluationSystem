package com.moyunzhijiao.system_frontend.mapper.template;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_frontend.entity.template.CustomTemplate;
import com.moyunzhijiao.system_frontend.entity.template.TeacherTemplate;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface TeacherTemplateMapper extends BaseMapper<TeacherTemplate> {
    @Select("select ct.* , tt.count as usageFrequency " +
            "from teacher_template tt left join custom_template ct on tt.template_id = ct.id " +
            "where tt.teacher_id = #{teacherId}")
    IPage<CustomTemplate> selectByTeacher(IPage<CustomTemplate> page, @Param("teacherId") Integer teacherId);

    @Select("select count(*) " +
            "from teacher_template tt left join custom_template ct on tt.template_id = ct.id " +
            "where tt.teacher_id = #{teacherId}")
    Integer countByTeacher(Integer teacherId);
}
