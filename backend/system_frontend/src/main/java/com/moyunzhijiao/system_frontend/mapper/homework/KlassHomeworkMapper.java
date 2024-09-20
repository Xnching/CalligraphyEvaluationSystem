package com.moyunzhijiao.system_frontend.mapper.homework;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_frontend.entity.Klass;
import com.moyunzhijiao.system_frontend.entity.homework.Homework;
import com.moyunzhijiao.system_frontend.entity.homework.KlassHomework;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface KlassHomeworkMapper extends BaseMapper<KlassHomework> {
    @Select("   SELECT h.name, h.id, AVG(hs.teacher_score) AS score " +
            "FROM klass_homework kh " +
            "   LEFT JOIN homework h ON kh.homework_id = h.id " +
            "   LEFT JOIN homework_submission hs ON h.id = hs.homework_id " +
            "WHERE kh.klass_id = #{klassId} AND h.deadline < NOW() " +
            "   GROUP BY h.id")
    public List<Homework> getDeadlineHomework(@Param("klassId") Integer klassId);

    @Select("select klass.name,klass.id ,avg(homework_submission.teacher_score) as score " +
            "from klass_homework " +
            "left join klass on klass.id = klass_homework.klass_id " +
            "left join homework_submission on homework_submission.homework_id = klass_homework.homework_id " +
            "where klass_homework.homework_id = #{homeworkId} " +
            "group by klass.name, klass.id")
    IPage<Klass> selectKlassByHomework(IPage<Klass> page, @Param("homeworkId") Integer homeworkId);
}
