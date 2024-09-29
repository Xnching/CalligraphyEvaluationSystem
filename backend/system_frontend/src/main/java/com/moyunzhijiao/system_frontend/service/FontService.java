package com.moyunzhijiao.system_frontend.service;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.entity.word.Font;
import com.moyunzhijiao.system_frontend.mapper.FontMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FontService extends ServiceImpl<FontMapper, Font> {
    @Autowired
    FontMapper fontMapper;
    public String getNameById(Integer id){
        if(ObjectUtil.isNull(id))
            return null;
        Font font = fontMapper.selectById(id);
        return font.getName();
    }

}
