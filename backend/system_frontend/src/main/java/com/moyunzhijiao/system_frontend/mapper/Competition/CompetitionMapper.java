package com.moyunzhijiao.system_frontend.mapper.Competition;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_frontend.controller.dto.CompetitionDTO;
import com.moyunzhijiao.system_frontend.entity.competition.Competition;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface CompetitionMapper extends BaseMapper<Competition> {
    @Select("SELECT id, name, picture as pictureURL " +
            "FROM competition ")
    public IPage<CompetitionDTO> getSelfCompetition(IPage<CompetitionDTO> page);

    @Select("SELECT COUNT(*) " +
            "FROM competition " )
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
}
