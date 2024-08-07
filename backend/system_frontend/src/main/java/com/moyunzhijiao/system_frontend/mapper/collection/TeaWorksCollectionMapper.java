package com.moyunzhijiao.system_frontend.mapper.collection;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_frontend.entity.collection.TeaWorksCollection;
import com.moyunzhijiao.system_frontend.entity.homework.Homework;
import org.apache.ibatis.annotations.Select;

public interface TeaWorksCollectionMapper extends BaseMapper<TeaWorksCollection> {

    @Select("SELECT " +
            "    twc.submission_id AS id," +
            "    twc.submission_type as type" +
            "    twc.created_time as created_time " +
            "    CASE  " +
            "        WHEN twc.submission_type = '作业作品' THEN hw.name " +
            "        WHEN twc.submission_type = '优秀竞赛作品' THEN oc.name " +
            "        WHEN twc.submission_type = '优秀作业作品' THEN oh.name " +
            "    END AS name " +
            "FROM  " +
            "    tea_works_collection twc " +
            "LEFT JOIN  " +
            "    homework hw ON twc.submission_id = hw.id AND twc.submission_type = '作业作品' " +
            "LEFT JOIN  " +
            "    outstanding_competition oc ON twc.submission_id = oc.submissions_id AND twc.submission_type = '优秀竞赛作品' " +
            "LEFT JOIN  " +
            "    outstanding_homework oh ON twc.submission_id = oh.submissions_id AND twc.submission_type = '优秀作业作品' " +
            "WHERE  " +
            "    twc.teacher_id = #{teacherId}; ")
    IPage<Homework> selectHomeworkOfTeacher(IPage<Homework> page, Integer teacherId);

    @Select("SELECT " +
            "    count(*)" +
            "FROM  " +
            "    tea_works_collection twc " +
            "WHERE  " +
            "    twc.teacher_id = #{teacherId}; ")
    Integer countHomeworkOfTeacher(Integer teacherId);
}
