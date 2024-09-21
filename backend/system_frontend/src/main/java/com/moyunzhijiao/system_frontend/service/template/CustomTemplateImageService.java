package com.moyunzhijiao.system_frontend.service.template;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.entity.template.CustomTemplateImage;
import com.moyunzhijiao.system_frontend.mapper.template.CustomTemplateImageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomTemplateImageService extends ServiceImpl<CustomTemplateImageMapper, CustomTemplateImage> {

    @Autowired
    CustomTemplateImageMapper customTemplateImageMapper;
    public List<String> getImages(Integer templateId){
        QueryWrapper<CustomTemplateImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("custom_template_id", templateId);
        queryWrapper.select("picture_url");
        List<CustomTemplateImage> customTemplateImages = customTemplateImageMapper.selectList(queryWrapper);
        return customTemplateImages.stream()
                .map(CustomTemplateImage::getPictureUrl)
                .collect(Collectors.toList());
    }

    public List<CustomTemplateImage> getByTemplate(Integer templateId) {
        QueryWrapper<CustomTemplateImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("custom_template_id",templateId);
        return list(queryWrapper);
    }

    public void deleteByTemplate(Integer templateId) {
        QueryWrapper<CustomTemplateImage>queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("custom_template_id",templateId);
        customTemplateImageMapper.delete(queryWrapper);
    }

    @Transactional
    public void addBatch(List<String> templateUrls, Integer id) {
        //批量保存图片
        List<CustomTemplateImage> images = templateUrls.stream().map(url->{
            CustomTemplateImage customTemplateImage = new CustomTemplateImage();
            customTemplateImage.setCustomTemplateId(id);
            customTemplateImage.setPictureUrl(url);
            return customTemplateImage;
        }).toList();
        saveBatch(images);
    }
}
