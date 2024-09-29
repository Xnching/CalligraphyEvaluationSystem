package com.moyunzhijiao.system_frontend.mapper.Competition;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_frontend.controller.dto.CompetitionDTO;
import com.moyunzhijiao.system_frontend.controller.dto.StudentDTO;
import com.moyunzhijiao.system_frontend.entity.competition.Competition;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CompetitionMapper extends BaseMapper<Competition> {
    @Select("SELECT id, name, picture as pictureURL " +
            "   FROM competition ")
    public IPage<CompetitionDTO> getSelfCompetition(IPage<CompetitionDTO> page);

    @Select("SELECT COUNT(*) " +
            "   FROM competition " )
    public Integer countSelfCompetition();

    @Select("SELECT " +
            "    c.id, " +
            "    c.name, " +
            "    c.picture AS pictureURL, " +
            "    cr.requirements " +
            "FROM " +
            "    competition c " +
            "LEFT JOIN " +
            "    competition_requirements cr ON c.id = cr.competition_id "+
            "WHERE" +
            "    c.id = #{id} ")
    public CompetitionDTO getDetail(@Param("id") String id);

    @Select("SELECT  " +
            "    c.id,  " +
            "    c.name,  " +
            "    c.picture as pictureURL,  " +
            "    c.competition_start_time as startTime, " +
            "    CASE  " +
            "        WHEN EXISTS (SELECT 1 FROM participant p WHERE p.competition_id = c.id AND p.student_id = #{stuId}) THEN false  " +
            "        ELSE true  " +
            "    END AS disabled " +
            "FROM  " +
            "    competition c " +
            "WHERE  " +
            "    c.state != '已结束' ")
    public IPage<CompetitionDTO> getExistingCompetition(IPage<CompetitionDTO> page,Integer stuId);

    @Select("SELECT COUNT(*) " +
            "FROM competition "+
            "where state!='已结束' " )
    public Integer countExistingCompetition();

    @Select("SELECT " +
            "  c.id, " +
            "  c.name, " +
            "  c.picture AS pictureURL, " +
            "  c.competition_start_time AS startTime, " +
            "  (" +
            "    SELECT " +
            "      cs.average_final_score " +
            "    FROM competition_submissions AS cs " +
            "    WHERE " +
            "      cs.author_id = #{id} AND cs.competition_id = c.id " +
            "  ) AS score " +
            "FROM competition AS c "+
            "where c.state='已结束'")
    public IPage<CompetitionDTO> getHistoryCompetition(IPage<CompetitionDTO> page,@Param("id") Integer id);

    @Select("SELECT COUNT(*) " +
            "FROM competition "+
            "where state='已结束' " )
    public Integer countHistoryCompetition();


}
