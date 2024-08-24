package com.moyunzhijiao.system_frontend.service.competition;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.controller.dto.CompetitionDTO;
import com.moyunzhijiao.system_frontend.controller.dto.HomeworkDTO;
import com.moyunzhijiao.system_frontend.entity.competition.Competition;
import com.moyunzhijiao.system_frontend.mapper.Competition.CompetitionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompetitionService extends ServiceImpl<CompetitionMapper, Competition> {
    @Autowired
    CompetitionMapper competitionMapper;

    public IPage<CompetitionDTO> getExistingCompetition(Integer currentPage, Integer pageSize){
        IPage<CompetitionDTO> page =new Page<>(currentPage,pageSize);
        Integer total;
        page= competitionMapper.getExistingCompetition(page);
        total= competitionMapper.countExistingCompetition();
        page.setTotal(total);
        return page;
    }

    public IPage<CompetitionDTO> getHistoryCompetition(Integer currentPage, Integer pageSize,Integer id){
        IPage<CompetitionDTO> page =new Page<>(currentPage,pageSize);
        Integer total;
        page= competitionMapper.getHistoryCompetition(page,id);
        total= competitionMapper.countHistoryCompetition();
        page.setTotal(total);
        return page;
    }
}

