package com.moyunzhijiao.system_frontend.service.outstanding;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.common.Constants;
import com.moyunzhijiao.system_frontend.entity.outstanding.UnreviewedOutstanding;
import com.moyunzhijiao.system_frontend.exception.ServiceException;
import com.moyunzhijiao.system_frontend.mapper.outstanding.UnreviewedOutstandingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnreviewedOutstandingService extends ServiceImpl<UnreviewedOutstandingMapper, UnreviewedOutstanding> {

    @Autowired
    UnreviewedOutstandingMapper unreviewedOutstandingMapper;
    public void addUnReviewed(Integer teacherId,Integer submissionId){
        QueryWrapper<UnreviewedOutstanding> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("submissions_id",submissionId);
        queryWrapper.eq("recommender_id",teacherId);
        Long count = unreviewedOutstandingMapper.selectCount(queryWrapper);
        if(count !=0){
            throw new ServiceException(Constants.CODE_400,"已推荐过！");
        }
        UnreviewedOutstanding unreviewedOutstanding = new UnreviewedOutstanding();
        unreviewedOutstanding.setRecommenderId(teacherId);
        unreviewedOutstanding.setSubmissionId(submissionId);
        save(unreviewedOutstanding);
    }




}
