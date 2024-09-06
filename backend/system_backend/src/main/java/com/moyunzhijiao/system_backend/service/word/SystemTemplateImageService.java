package com.moyunzhijiao.system_backend.service.word;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.entiy.competition.CSubmissionImage;
import com.moyunzhijiao.system_backend.entiy.word.SystemTemplateImage;
import com.moyunzhijiao.system_backend.mapper.word.SystemTemplateImageMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SystemTemplateImageService extends ServiceImpl<SystemTemplateImageMapper, SystemTemplateImage> {
    public List<String> getImages(Integer templateId){
        QueryWrapper<SystemTemplateImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("system_template_id",templateId);
        return list(queryWrapper).stream().map(SystemTemplateImage::getPictureUrl).collect(Collectors.toList());
    }
}
