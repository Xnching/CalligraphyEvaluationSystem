package com.moyunzhijiao.system_frontend.service.template;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.entity.template.CustomTemplate;
import com.moyunzhijiao.system_frontend.mapper.template.CustomTemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomTemplateService extends ServiceImpl<CustomTemplateMapper, CustomTemplate> {
    @Autowired
    CustomTemplateMapper customTemplateMapper;
    @Autowired
    CustomTemplateImageService customTemplateImageService;
    @Autowired
    TeacherTemplateService teacherTemplateService;
    /*
    * 下面是通过教师发布作业，里面找是否有自定义的模板，教师获取保存的模板换了，换成teacherTemplateService
    * */
    public IPage<CustomTemplate> getByTeacher(Integer teacherId, Integer currentPage, Integer pageSize) {
        IPage<CustomTemplate> page = new Page<>(currentPage,pageSize);
        page = customTemplateMapper.selectByTeacher(page,teacherId);
        Integer total = customTemplateMapper.countByTeacher(teacherId);
        total = total==null?0:total;
        page.setTotal(total);
        return page;
    }

    public CustomTemplate getDeatilByTeacherSingle(Integer templateId){
        CustomTemplate customTemplate = customTemplateMapper.selectById(templateId);
        customTemplate.setImgs(customTemplateImageService.getImages(templateId));
        return customTemplate;
    }
}
