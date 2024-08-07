package com.moyunzhijiao.system_frontend.service.Help;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.common.Constants;
import com.moyunzhijiao.system_frontend.controller.dto.QAFDTO;
import com.moyunzhijiao.system_frontend.entity.help.Feedback;
import com.moyunzhijiao.system_frontend.entity.help.FeedbackContent;
import com.moyunzhijiao.system_frontend.exception.ServiceException;
import com.moyunzhijiao.system_frontend.mapper.help.FeedbackContentMapper;
import com.moyunzhijiao.system_frontend.mapper.help.FeedbackMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FeedbackService extends ServiceImpl<FeedbackMapper, Feedback> {

    @Autowired
    FeedbackMapper feedbackMapper;
    @Autowired
    FeedbackContentMapper feedbackContentMapper;
    public QAFDTO getFeedback(String type,Integer id){
        QAFDTO qafdto = new QAFDTO();
        qafdto.setList(feedbackMapper.selectFeedbackById(type, id));
        qafdto.setType("2");
        return qafdto;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void insertFeedback(Feedback feedback) {
        try {
            feedback.setState("未回复");
            FeedbackContent feedbackContent = new FeedbackContent();
            feedbackMapper.insert(feedback);
            feedbackContent.setFeedbackId(feedback.getId());
            feedbackContent.setMessage(feedback.getContent());
            feedbackContentMapper.insert(feedbackContent);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(Constants.CODE_500,"反馈失败！原因为："+e);
        }
    }
}
