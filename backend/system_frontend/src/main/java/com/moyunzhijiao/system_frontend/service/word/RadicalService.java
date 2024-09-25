package com.moyunzhijiao.system_frontend.service.word;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.entity.word.Radical;
import com.moyunzhijiao.system_frontend.mapper.word.RadicalMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RadicalService extends ServiceImpl<RadicalMapper, Radical> {

    public List<Radical> getByCount(Integer count){
        QueryWrapper<Radical> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("count",count);
        return list(queryWrapper);
    }
}
