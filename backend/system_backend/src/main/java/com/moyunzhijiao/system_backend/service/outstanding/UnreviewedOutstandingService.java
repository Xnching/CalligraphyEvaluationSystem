package com.moyunzhijiao.system_backend.service.outstanding;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.controller.dto.outstanding.UnreviewedOutstandingDTO;
import com.moyunzhijiao.system_backend.entiy.outstanding.UnreviewedOutstanding;
import com.moyunzhijiao.system_backend.mapper.outstanding.UnreviewedOutstandingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnreviewedOutstandingService extends ServiceImpl<UnreviewedOutstandingMapper, UnreviewedOutstanding> {
    @Autowired
    UnreviewedOutstandingMapper unreviewedOutstandingMapper;
    public IPage<UnreviewedOutstandingDTO> getPage(IPage<UnreviewedOutstandingDTO> page, String str) {
        page = unreviewedOutstandingMapper.page(page,str);
        int total = Math.toIntExact(unreviewedOutstandingMapper.selectCount(new QueryWrapper<>()));
        page.setTotal(total);
        return page;
    }
}
