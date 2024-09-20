package com.moyunzhijiao.system_frontend.service.outstanding;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.controller.dto.OutstandingCompetitionDTO;
import com.moyunzhijiao.system_frontend.entity.outstanding.OutstandingCompetition;
import com.moyunzhijiao.system_frontend.mapper.outstanding.OutstandingCompetitionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OutstandingCompetitionService extends ServiceImpl<OutstandingCompetitionMapper, OutstandingCompetition> {
    @Autowired
    OutstandingCompetitionMapper outstandingCompetitionMapper;

    public IPage<OutstandingCompetitionDTO> getList(Integer currentPage, Integer pageSize){
        IPage<OutstandingCompetitionDTO> page =new Page<>(currentPage,pageSize);
        Integer total;
        page= outstandingCompetitionMapper.getOutstandingCompetition(page);
        total= outstandingCompetitionMapper.countOutstandingCompetition();
        page.setTotal(total);
        return page;
    }
}
