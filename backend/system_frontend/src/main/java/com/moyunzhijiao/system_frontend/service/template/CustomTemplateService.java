package com.moyunzhijiao.system_frontend.service.template;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.common.Constants;
import com.moyunzhijiao.system_frontend.entity.template.CustomTemplate;
import com.moyunzhijiao.system_frontend.exception.ServiceException;
import com.moyunzhijiao.system_frontend.mapper.template.CustomTemplateMapper;
import com.moyunzhijiao.system_frontend.service.FontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomTemplateService extends ServiceImpl<CustomTemplateMapper, CustomTemplate> {
    @Autowired
    CustomTemplateMapper customTemplateMapper;
    @Autowired
    CustomTemplateImageService customTemplateImageService;
    @Autowired
    FontService fontService;

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
        customTemplate.setFont(fontService.getNameById(customTemplate.getFontId()));
        return customTemplate;
    }


    /*
     * 获取一个教师的已保存的自定义模板,下面是有teacher_template表的时候的获取
     * */
//    public IPage<CustomTemplate> getByTeacher(Integer teacherId, Integer currentPage, Integer pageSize) {
//        IPage<CustomTemplate> page = new Page<>(currentPage,pageSize);
//        page = teacherTemplateMapper.selectByTeacher(page,teacherId);
//        Integer total = teacherTemplateMapper.countByTeacher(teacherId);
//        total = total==null?0:total;
//        page.setTotal(total);
//        return page;
//    }

    /*
     * 把教师的一个保存下来的模板删除，同时把对应自定义模板删除
     * */
    public void deleteTemplateOfTeacher(Integer templateId){
        //首先删除关系
        QueryWrapper<CustomTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",templateId);
        try {
            customTemplateMapper.delete(queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(Constants.CODE_500,"参数或系统错误，删除关系失败");
        }
    }
}
