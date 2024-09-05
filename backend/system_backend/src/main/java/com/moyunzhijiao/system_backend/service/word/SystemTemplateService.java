package com.moyunzhijiao.system_backend.service.word;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.entiy.word.SystemTemplate;
import com.moyunzhijiao.system_backend.mapper.word.SystemTemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemTemplateService extends ServiceImpl<SystemTemplateMapper, SystemTemplate> {
    @Autowired
    SystemTemplateMapper systemTemplateMapper;
    @Autowired
    SystemTemplateImageService systemTemplateImageService;
    public IPage<SystemTemplate> selectPage(IPage<SystemTemplate> page, String str, String type) {
        QueryWrapper<SystemTemplate> queryWrapper = new QueryWrapper<>();
        if(!StrUtil.isEmpty(str)){
            queryWrapper.like("name",str);
        }
        if(!StrUtil.isEmpty(type)){
            queryWrapper.eq("type",type);
        }
        page = systemTemplateMapper.selectPage(page,queryWrapper);
        page.getRecords().forEach(systemTemplate -> {
            Integer templateId = systemTemplate.getId();
            systemTemplate.setImageList(systemTemplateImageService.getImages(templateId));
        });
        return page;
    }
}
