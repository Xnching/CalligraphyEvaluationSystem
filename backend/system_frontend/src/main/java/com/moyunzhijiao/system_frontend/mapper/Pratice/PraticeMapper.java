package com.moyunzhijiao.system_frontend.mapper.Pratice;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_frontend.controller.dto.PracticeDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface PraticeMapper extends BaseMapper<PracticeDTO> {

    @Select("SELECT " +
            "  h.id," +
            "  h.name," +
            "(COALESCE(hs.system_score, 0) + COALESCE(hs.teacher_score, 0)) / 2 AS score "+
            "FROM homework AS h " +
            "JOIN homework_submission AS hs " +
            "  ON h.id = hs.homework_id " +
            "WHERE " +
            "  hs.student_id = #{id} AND hs.state = 1 AND h.is_self = 1 ")
    public IPage<PracticeDTO> getPraticeFinished(IPage<PracticeDTO> page, @Param("id") Integer id);

    @Select("select count(*) "+
            "FROM homework AS h " +
            "JOIN homework_submission AS hs " +
            "  ON h.id = hs.homework_id " +
            "WHERE " +
            "  hs.student_id = #{id} AND hs.state = 1 AND h.is_self = 1 ")
    public Integer countPraticeFinished(Integer id);

    @Select("SELECT " +
            "  h.id," +
            "  h.name," +
            "(COALESCE(hs.system_score, 0) + COALESCE(hs.teacher_score, 0)) / 2 AS score "+
            "FROM homework AS h " +
            "JOIN homework_submission AS hs " +
            "  ON h.id = hs.homework_id " +
            "WHERE " +
            "  hs.student_id = #{id} AND hs.state = 0 AND h.is_self = 1 ")
    public IPage<PracticeDTO> getPraticeUnfinished(IPage<PracticeDTO> page,@Param("id") Integer id);

    @Select("select count(*) "+
            "FROM homework AS h " +
            "JOIN homework_submission AS hs " +
            "  ON h.id = hs.homework_id " +
            "WHERE " +
            "  hs.student_id = #{id} AND hs.state = 0 AND h.is_self = 1 ")
    public Integer countPraticeUnfinished(Integer id);
}
