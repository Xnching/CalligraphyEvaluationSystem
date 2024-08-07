package com.moyunzhijiao.system_frontend.service.template;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.common.Constants;
import com.moyunzhijiao.system_frontend.entity.template.CustomTemplate;
import com.moyunzhijiao.system_frontend.entity.template.TeacherTemplate;
import com.moyunzhijiao.system_frontend.exception.ServiceException;
import com.moyunzhijiao.system_frontend.mapper.template.TeacherTemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherTemplateService extends ServiceImpl<TeacherTemplateMapper, TeacherTemplate> {
    @Autowired
    TeacherTemplateMapper teacherTemplateMapper;
    @Autowired
    CustomTemplateService customTemplateService;
    /*
    * 获取一个教师的已保存的自定义模板
    * */
    public IPage<CustomTemplate> getByTeacher(Integer teacherId, Integer currentPage, Integer pageSize) {
        IPage<CustomTemplate> page = new Page<>(currentPage,pageSize);
        page = teacherTemplateMapper.selectByTeacher(page,teacherId);
        Integer total = teacherTemplateMapper.countByTeacher(teacherId);
        total = total==null?0:total;
        page.setTotal(total);
        return page;
    }
    /*
    * 把教师的一个保存下来的模板删除，同时把对应自定义模板删除
    * */
    public void deleteTemplateOfTeacher(Integer teacherId,Integer templateId){
        //首先删除关系
        QueryWrapper<TeacherTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id",teacherId);
        queryWrapper.eq("template_id",templateId);
        try {
            teacherTemplateMapper.delete(queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(Constants.CODE_500,"参数或系统错误，删除关系失败");
        }
        //接着删除自定义模板
        try {
            customTemplateService.removeById(teacherId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(Constants.CODE_500,"参数或系统错误，删除自定义模板失败");
        }
    }


}
