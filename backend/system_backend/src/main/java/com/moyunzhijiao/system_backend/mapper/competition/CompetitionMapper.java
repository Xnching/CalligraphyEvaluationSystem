package com.moyunzhijiao.system_backend.mapper.competition;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moyunzhijiao.system_backend.entiy.competition.Competition;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface CompetitionMapper extends BaseMapper<Competition> {

    @Update("UPDATE competition c SET c.state = CASE " +
            "WHEN c.state NOT IN ('已结束') THEN " +
            "CASE " +
            "WHEN c.registration_start_time > #{now} THEN '准备报名中' " +
            "WHEN c.registration_start_time <= #{now} AND c.registration_end_time > #{now} THEN '报名中' " +
            "WHEN c.registration_end_time <= #{now} AND c.competition_start_time > #{now} THEN '准备竞赛中' " +
            "WHEN c.competition_start_time <= #{now} AND c.competition_end_time > #{now} THEN '竞赛中' " +
            "WHEN c.competition_end_time <= #{now} AND c.review_start_time > #{now} THEN '准备评阅中' " +
            "WHEN c.review_start_time <= #{now} THEN '初级评阅中' " +
            "ELSE c.state END")
    void updateStatus(@Param("now") LocalDateTime now);
}
