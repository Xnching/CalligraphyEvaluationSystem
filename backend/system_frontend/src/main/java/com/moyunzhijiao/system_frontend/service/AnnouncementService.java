package com.moyunzhijiao.system_frontend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.entity.Announcement;
import com.moyunzhijiao.system_frontend.mapper.AnnouncementMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnnouncementService extends ServiceImpl<AnnouncementMapper, Announcement> {

    public List<Announcement> findOtherCompetition(){
        LocalDateTime now = LocalDateTime.now();
        QueryWrapper<Announcement> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", "第三方竞赛公告")
                .and(wrapper -> wrapper.eq("target", "全体")
                        .or()
                        .eq("target", "教师"))
                .lt("release_time", now);
        queryWrapper.select("id","name","release_time");
        return list(queryWrapper);
    }
}
