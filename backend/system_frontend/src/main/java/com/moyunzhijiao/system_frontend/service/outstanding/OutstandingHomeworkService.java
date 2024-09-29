package com.moyunzhijiao.system_frontend.service.outstanding;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.controller.dto.OutstandingCompetitionDTO;
import com.moyunzhijiao.system_frontend.controller.dto.OutstandingHomeworkDTO;
import com.moyunzhijiao.system_frontend.entity.outstanding.OutstandingHomework;
import com.moyunzhijiao.system_frontend.mapper.outstanding.OutstandingCompetitionMapper;
import com.moyunzhijiao.system_frontend.mapper.outstanding.OutstandingHomeworkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OutstandingHomeworkService extends ServiceImpl<OutstandingHomeworkMapper, OutstandingHomework> {
    @Autowired
    OutstandingHomeworkMapper outstandingHomeworkMapper;

    public IPage<OutstandingHomeworkDTO> getList(Integer currentPage, Integer pageSize){
        IPage<OutstandingHomeworkDTO> page =new Page<>(currentPage,pageSize);
        Integer total;
        page= outstandingHomeworkMapper.getOutstandingHomework(page);
        total= outstandingHomeworkMapper.countOutstandingHomework();
        page.setTotal(total);
        return page;
    }

}
