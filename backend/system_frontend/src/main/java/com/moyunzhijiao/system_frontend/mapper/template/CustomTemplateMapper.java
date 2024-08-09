package com.moyunzhijiao.system_frontend.mapper.template;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_frontend.entity.template.CustomTemplate;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface CustomTemplateMapper extends BaseMapper<CustomTemplate> {
    @Select("SELECT " +
            "    ct.* " +
            "FROM  " +
            "    custom_template ct WHERE ct.creator_id = #{teacherId} ")
    IPage<CustomTemplate> selectByTeacher(IPage<CustomTemplate> page, @Param("teacherId") Integer teacherId);

    @Select("SELECT " +
            "    count(*) " +
            "FROM  " +
            "    custom_template ct WHERE  " +
            "    ct.creator_id = #{teacherId} ")
    Integer countByTeacher(@Param("teacherId") Integer teacherId);


    //下面是有teacher_template时候的表
//    @Select("select ct.* , tt.count as usageFrequency " +
//            "from teacher_template tt left join custom_template ct on tt.template_id = ct.id " +
//            "where tt.teacher_id = #{teacherId}")
//    IPage<CustomTemplate> selectByTeacher(IPage<CustomTemplate> page, @Param("teacherId") Integer teacherId);
//
//    @Select("select count(*) " +
//            "from teacher_template tt left join custom_template ct on tt.template_id = ct.id " +
//            "where tt.teacher_id = #{teacherId}")
//    Integer countByTeacher(Integer teacherId);
}
