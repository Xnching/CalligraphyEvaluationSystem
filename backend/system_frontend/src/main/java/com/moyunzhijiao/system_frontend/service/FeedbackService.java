package com.moyunzhijiao.system_frontend.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.controller.dto.QAFDTO;
import com.moyunzhijiao.system_frontend.entity.Feedback;
import com.moyunzhijiao.system_frontend.mapper.FeedbackMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService extends ServiceImpl<FeedbackMapper, Feedback> {

    @Autowired
    FeedbackMapper feedbackMapper;
    public QAFDTO getFeedback(String type,Integer id){
        QAFDTO qafdto = new QAFDTO();
        qafdto.setList(feedbackMapper.selectFeedbackById(type, id));
        qafdto.setType("2");
        return qafdto;
    }
}
