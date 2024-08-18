package com.moyunzhijiao.system_backend.service.announcementHelp;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.moyunzhijiao.system_backend.common.Constants;
import com.moyunzhijiao.system_backend.entiy.announcementHelp.Feedback;
import com.moyunzhijiao.system_backend.entiy.announcementHelp.FeedbackContent;
import com.moyunzhijiao.system_backend.exception.ServiceException;

import com.moyunzhijiao.system_backend.mapper.announcementHelp.FeedbackMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FeedbackService extends ServiceImpl<FeedbackMapper, Feedback> {


}
