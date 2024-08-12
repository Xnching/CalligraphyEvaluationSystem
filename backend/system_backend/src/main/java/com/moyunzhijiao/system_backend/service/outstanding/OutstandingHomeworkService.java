package com.moyunzhijiao.system_backend.service.outstanding;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.controller.dto.outstanding.OutstandingHomeworkDTO;
import com.moyunzhijiao.system_backend.entiy.outstanding.OutstandingHomework;
import com.moyunzhijiao.system_backend.mapper.outstanding.OutstandingHomeworkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OutstandingHomeworkService extends ServiceImpl<OutstandingHomeworkMapper, OutstandingHomework> {
    @Autowired
    OutstandingHomeworkMapper outstandingHomeworkMapper;
    public IPage<OutstandingHomeworkDTO> getPage(IPage<OutstandingHomeworkDTO> page, String str) {
        page = outstandingHomeworkMapper.page(page,str);
        long total = outstandingHomeworkMapper.selectCount(new QueryWrapper<>());
        page.setTotal(total);
        return page;
    }
}
