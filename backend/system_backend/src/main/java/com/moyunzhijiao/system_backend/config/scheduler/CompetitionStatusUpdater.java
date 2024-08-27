package com.moyunzhijiao.system_backend.config.scheduler;

import com.moyunzhijiao.system_backend.mapper.competition.CompetitionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class CompetitionStatusUpdater {
    @Autowired
    private CompetitionMapper competitionMapper;

    /*
    * 下面是根据时间自动更新竞赛状态的定时任务
    * */
    @Transactional
    @Scheduled(fixedRate = 600000) // 每分钟执行一次
    public void updateCompetitionStatus() {
        LocalDateTime now = LocalDateTime.now();
        competitionMapper.updateStatus(now);
    }
}
