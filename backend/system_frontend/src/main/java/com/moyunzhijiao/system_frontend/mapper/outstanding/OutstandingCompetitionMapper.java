package com.moyunzhijiao.system_frontend.mapper.outstanding;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_frontend.controller.dto.ArticleDTO;
import com.moyunzhijiao.system_frontend.controller.dto.OutstandingCompetitionDTO;
import com.moyunzhijiao.system_frontend.entity.outstanding.OutstandingCompetition;
import org.apache.ibatis.annotations.Select;

public interface OutstandingCompetitionMapper extends BaseMapper<OutstandingCompetition> {
    @Select("SELECT  " +
            "  cs.id, " +
            "  oc.created_time AS submitTime, " +
            "  c.name " +
            "FROM outstanding_competition AS oc " +
            "JOIN competition_submissions AS cs " +
            "  ON oc.submissions_id = cs.id " +
            "JOIN competition AS c " +
            "  ON cs.competition_id = c.id ")
    public IPage<OutstandingCompetitionDTO> getOutstandingCompetition(IPage<OutstandingCompetitionDTO> page);

    @Select("SELECT COUNT(*) " +
            "FROM outstanding_competition AS oc " +
            "JOIN competition_submissions AS cs " +
            "  ON oc.submissions_id = cs.id " +
            "JOIN competition AS c " +
            "  ON cs.competition_id = c.id ")
    public Integer countOutstandingCompetition();
}
