package com.moyunzhijiao.system_frontend.service.resource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.entity.resource.CalligraphyFacts;
import com.moyunzhijiao.system_frontend.mapper.resource.CalligraphyFactsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalligraphyFactsService extends ServiceImpl<CalligraphyFactsMapper, CalligraphyFacts> {
    @Autowired
    CalligraphyFactsMapper calligraphyFactsMapper;
    /*
    * 获取字的书写的id
    * */
    public Integer getWriteWordId(){
        CalligraphyFacts calligraphyFacts;
        QueryWrapper<CalligraphyFacts> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name","字的书写");
        calligraphyFacts = calligraphyFactsMapper.selectOne(queryWrapper);
        return calligraphyFacts.getId();
    }
}
