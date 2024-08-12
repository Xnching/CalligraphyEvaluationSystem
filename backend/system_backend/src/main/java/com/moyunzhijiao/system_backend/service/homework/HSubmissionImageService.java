package com.moyunzhijiao.system_backend.service.homework;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.entiy.homework.HSubmissionImage;
import com.moyunzhijiao.system_backend.mapper.homework.HSubmissionImageMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HSubmissionImageService extends ServiceImpl<HSubmissionImageMapper, HSubmissionImage> {

    /*
    * 获得图片列表
    * */
    public List<String> getImages(Integer submissionId) {
        QueryWrapper<HSubmissionImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("submission_id",submissionId);
        return list(queryWrapper).stream().map(HSubmissionImage::getPictureUrl).collect(Collectors.toList());
    }
}
