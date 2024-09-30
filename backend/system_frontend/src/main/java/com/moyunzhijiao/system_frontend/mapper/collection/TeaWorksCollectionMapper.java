package com.moyunzhijiao.system_frontend.mapper.collection;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_frontend.entity.collection.TeaWorksCollection;
import com.moyunzhijiao.system_frontend.entity.homework.Homework;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface TeaWorksCollectionMapper extends BaseMapper<TeaWorksCollection> {

    @Select("SELECT " +
            "    twc.submission_id AS id, " +
            "    twc.type as type ," +
            "    twc.created_time as created_time ," +
            "    CASE  " +
            "        WHEN twc.type = '作业作品' THEN hw.name " +
            "        WHEN twc.type = '竞赛作品' THEN cs.name " +
            "    END AS name " +
            "FROM  " +
            "    tea_works_collection twc " +
            "LEFT JOIN  " +
            "    homework hw ON twc.submission_id = hw.id AND twc.type = '作业作品' " +
            "LEFT JOIN  " +
            "    competition_submissions cs ON twc.submission_id = cs.id AND twc.type = '竞赛作品' " +
            "WHERE  " +
            "    twc.teacher_id = #{teacherId} ")
    IPage<Homework> selectHomeworkOfTeacher(IPage<Homework> page, @Param("teacherId") Integer teacherId);

    @Select("SELECT " +
            "    count(*)" +
            "FROM  " +
            "    tea_works_collection twc " +
            "WHERE  " +
            "    twc.teacher_id = #{teacherId} ")
    Integer countHomeworkOfTeacher(@Param("teacherId") Integer teacherId);
}
