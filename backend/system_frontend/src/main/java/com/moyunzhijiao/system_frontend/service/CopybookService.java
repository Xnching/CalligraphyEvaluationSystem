package com.moyunzhijiao.system_frontend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.entity.Copybook;
import com.moyunzhijiao.system_frontend.mapper.CopybookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CopybookService extends ServiceImpl<CopybookMapper, Copybook> {
    @Autowired
    CopybookMapper copybookMapper;
    public IPage<Copybook> getPageAndFont(IPage<Copybook> page){
        page = copybookMapper.selectPageAndFont(page);
        Integer total = Math.toIntExact(copybookMapper.selectCount(new QueryWrapper<>()));
        page.setTotal(total);
        return page;
    }
}
