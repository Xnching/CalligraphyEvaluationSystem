package com.moyunzhijiao.system_frontend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moyunzhijiao.system_frontend.controller.dto.QAFDTO;
import com.moyunzhijiao.system_frontend.entity.Feedback;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FeedbackMapper extends BaseMapper<Feedback> {
    @Select("select feedback_content.message as title,feedback_content.reply as content ,feedback.type as type " +
            "from feedback left join feedback_content on feedback.id = feedback_content.feedback_id " +
            "where feedback.provider_id = #{providerId} and feedback.provider_type = #{providerType} ")
    public List<QAFDTO.Item> selectFeedbackById(@Param("providerType") String type,@Param("providerId") Integer id);
}
