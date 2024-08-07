package com.moyunzhijiao.system_frontend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.entity.TemplateWord;
import com.moyunzhijiao.system_frontend.mapper.TemplateWordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemplateWordService extends ServiceImpl<TemplateWordMapper, TemplateWord> {
    @Autowired
    TemplateWordMapper templateWordMapper;
    /*
    * 根据部首查找对应的模板字
    * */
    public List<TemplateWord> getByRadical(Integer radicalId) {
        QueryWrapper<TemplateWord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("radical_id",radicalId);
        queryWrapper.select("id","content");
        return templateWordMapper.selectList(queryWrapper);
    }

    /*
     * 根据结构查找对应的模板字
     * */
    public List<TemplateWord> getByStructure(Integer structureId) {
        QueryWrapper<TemplateWord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("structure_id",structureId);
        queryWrapper.select("id","content");
        return templateWordMapper.selectList(queryWrapper);
    }
}
