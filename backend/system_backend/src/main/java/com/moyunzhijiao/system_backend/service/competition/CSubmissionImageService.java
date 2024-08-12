package com.moyunzhijiao.system_backend.service.competition;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.entiy.competition.CSubmissionImage;
import com.moyunzhijiao.system_backend.entiy.homework.HSubmissionImage;
import com.moyunzhijiao.system_backend.mapper.competition.CSubmissionImageMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CSubmissionImageService extends ServiceImpl<CSubmissionImageMapper, CSubmissionImage> {
    public List<String> getImages(Integer submissionId) {
        QueryWrapper<CSubmissionImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("submission_id",submissionId);
        return list(queryWrapper).stream().map(CSubmissionImage::getPictureUrl).collect(Collectors.toList());
    }
}
