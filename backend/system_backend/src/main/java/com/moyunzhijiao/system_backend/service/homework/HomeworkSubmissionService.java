package com.moyunzhijiao.system_backend.service.homework;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.controller.dto.homework.HomeworkSubmissionDTO;
import com.moyunzhijiao.system_backend.entiy.homework.HomeworkSubmission;
import com.moyunzhijiao.system_backend.mapper.homework.HSubmissionImageMapper;
import com.moyunzhijiao.system_backend.mapper.homework.HomeworkSubmissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeworkSubmissionService extends ServiceImpl<HomeworkSubmissionMapper, HomeworkSubmission> {
    @Autowired
    HSubmissionImageService hSubmissionImageService;
    @Autowired
    HomeworkSubmissionMapper homeworkSubmissionMapper;
    /*
    * 获得一个作业的详细信息，以及其图片列表
    * */
    public HomeworkSubmissionDTO getDetail(Integer submissionId) {
        HomeworkSubmission homeworkSubmission = getById(submissionId);
        HomeworkSubmissionDTO homeworkSubmissionDTO = new HomeworkSubmissionDTO();
        //设置基本信息
        BeanUtil.copyProperties(homeworkSubmission,homeworkSubmissionDTO);
        //设置图片列表
        homeworkSubmissionDTO.setImageSrcList(hSubmissionImageService.getImages(submissionId));
        return homeworkSubmissionDTO;
    }
}
