package com.moyunzhijiao.system_frontend.mapper.homework;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_frontend.controller.dto.ArticleDTO;
import com.moyunzhijiao.system_frontend.controller.dto.HomeworkDTO;
import com.moyunzhijiao.system_frontend.entity.homework.Homework;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface HomeworkMapper extends BaseMapper<Homework> {

    @Select("SELECT " +
            "  h.id," +
            "  h.name," +
            "  h.target," +
            "  h.deadline " +
            "FROM homework AS h " +
            "JOIN homework_submission AS hs " +
            "  ON h.id = hs.homework_id " +
            "WHERE " +
            "  hs.student_id = #{id} AND hs.state = 1 AND h.is_self = 0 ")
    public IPage<HomeworkDTO> getHomeworkFinished(IPage<HomeworkDTO> page,@Param("id") Integer id);

    @Select("select count(*) "+
            "FROM homework AS h " +
            "JOIN homework_submission AS hs " +
            "  ON h.id = hs.homework_id " +
            "WHERE " +
            "  hs.student_id = #{id} AND hs.state = 1 AND h.is_self = 0 ")
    public Integer countHomeworkFinished(Integer id);

    @Select("SELECT " +
            "  h.id," +
            "  h.name," +
            "  h.target," +
            "  h.deadline " +
            "FROM homework AS h " +
            "JOIN homework_submission AS hs " +
            "  ON h.id = hs.homework_id " +
            "WHERE " +
            "  hs.student_id = #{id} AND hs.state = 0 AND h.is_self = 0 ")
    public IPage<HomeworkDTO> getHomeworkUnfinished(IPage<HomeworkDTO> page,@Param("id") Integer id);

    @Select("select count(*) "+
            "FROM homework AS h " +
            "JOIN homework_submission AS hs " +
            "  ON h.id = hs.homework_id " +
            "WHERE " +
            "  hs.student_id = #{id} AND hs.state = 0 AND h.is_self = 0 ")
    public Integer countHomeworkUnfinished(Integer id);
}
