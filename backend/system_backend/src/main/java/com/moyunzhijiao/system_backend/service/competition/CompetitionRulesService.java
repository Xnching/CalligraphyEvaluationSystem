package com.moyunzhijiao.system_backend.service.competition;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.entiy.competition.CompetitionRules;
import com.moyunzhijiao.system_backend.mapper.competition.CompetitionRulesMapper;
import com.moyunzhijiao.system_backend.mapper.competition.CompetitionSubmissionsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompetitionRulesService extends ServiceImpl<CompetitionRulesMapper, CompetitionRules> {

    @Autowired
    CompetitionRulesMapper competitionRulesMapper;
    public Integer getByField(String str) {
        QueryWrapper<CompetitionRules> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("question",str);
        CompetitionRules competitionRules = competitionRulesMapper.selectOne(queryWrapper);
        return competitionRules.getAnswer();
    }
}
