package com.moyunzhijiao.system_backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.entiy.Font;
import com.moyunzhijiao.system_backend.entiy.Radical;
import com.moyunzhijiao.system_backend.mapper.FontMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FontService extends ServiceImpl<FontMapper, Font> {

    @Autowired
    FontMapper fontMapper;
    public Integer getIdByField(String name){
        QueryWrapper<Font> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id").eq("name",name);
        Font font = fontMapper.selectOne(queryWrapper);
        return font !=null ? font.getId():null;
    }
}
