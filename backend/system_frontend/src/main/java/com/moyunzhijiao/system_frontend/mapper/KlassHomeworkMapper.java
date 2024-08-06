package com.moyunzhijiao.system_frontend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moyunzhijiao.system_frontend.entity.Homework;
import com.moyunzhijiao.system_frontend.entity.KlassHomework;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface KlassHomeworkMapper extends BaseMapper<KlassHomework> {
    @Select("SELECT h.name, h.id, AVG(hs.teacher_score) AS score " +
            "FROM klass_homework kh " +
            "LEFT JOIN homework h ON kh.homework_id = h.id " +
            "LEFT JOIN homework_submission hs ON h.id = hs.homework_id " +
            "WHERE kh.klass_id = #{klassId} AND h.deadline < NOW() " +
            "GROUP BY h.id")
    public List<Homework> getDeadlineHomework(@Param("klassId") Integer klassId);
}
