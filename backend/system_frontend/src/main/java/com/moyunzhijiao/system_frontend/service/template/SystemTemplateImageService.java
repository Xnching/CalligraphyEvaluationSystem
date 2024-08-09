package com.moyunzhijiao.system_frontend.service.template;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.entity.template.SystemTemplateImage;
import com.moyunzhijiao.system_frontend.mapper.template.SystemTemplateImageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SystemTemplateImageService extends ServiceImpl<SystemTemplateImageMapper, SystemTemplateImage> {
    @Autowired
    SystemTemplateImageMapper systemTemplateImageMapper;
    public List<String> getImages(Integer templateId){
        QueryWrapper<SystemTemplateImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("picture_url");
        queryWrapper.eq("system_template_id",templateId);
        List<SystemTemplateImage> images = systemTemplateImageMapper.selectList(queryWrapper);
        return images.stream()
                .map(SystemTemplateImage::getPictureUrl)
                .collect(Collectors.toList());
    }


}
