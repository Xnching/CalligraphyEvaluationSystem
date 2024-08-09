package com.moyunzhijiao.system_frontend.service.template;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.entity.template.SystemTemplate;
import com.moyunzhijiao.system_frontend.mapper.template.SystemTemplateMapper;
import com.moyunzhijiao.system_frontend.service.FontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemTemplateService extends ServiceImpl<SystemTemplateMapper, SystemTemplate> {
    @Autowired
    SystemTemplateMapper systemTemplateMapper;
    @Autowired
    SystemTemplateImageService systemTemplateImageService;
    @Autowired
    FontService fontService;
    public IPage<SystemTemplate> getByTeacher(Integer teacherId, Integer currentPage, Integer pageSize) {
        IPage<SystemTemplate> page = new Page<>(currentPage,pageSize);
        page = systemTemplateMapper.selectByTeacher(page,teacherId);
        Integer total = systemTemplateMapper.countByTeacher();
        total = total==null?0:total;
        page.setTotal(total);
        return page;
    }

    public SystemTemplate getDetailByTeacherSingle(Integer templateId){
        SystemTemplate systemTemplate = systemTemplateMapper.selectById(templateId);
        systemTemplate.setImgs(systemTemplateImageService.getImages(templateId));
        systemTemplate.setFont(fontService.getNameById(systemTemplate.getFontId()));
        return systemTemplate;
    }
}
