package com.moyunzhijiao.system_backend.service.competition;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.entiy.competition.Competition;
import com.moyunzhijiao.system_backend.entiy.competition.Participant;
import com.moyunzhijiao.system_backend.entiy.front.Student;
import com.moyunzhijiao.system_backend.mapper.competition.CompetitionMapper;
import com.moyunzhijiao.system_backend.mapper.competition.ParticipantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompetitionService extends ServiceImpl<CompetitionMapper, Competition> {
    @Autowired
    CompetitionMapper competitionMapper;
    @Autowired
    DivisionService divisionService;

    @Autowired
    ParticipantMapper participantMapper;
    /*
    * 获取所有竞赛列表
    * */
    public IPage<Competition> selectPage(IPage<Competition> page, String str) {
        QueryWrapper<Competition> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name",str);
        queryWrapper.like("state",str);
        queryWrapper.eq("delete_flag",0);
        return competitionMapper.selectPage(page,queryWrapper);
    }

    /*
    * 逻辑删除一个竞赛以及对应的组别
    * */
    public void deleteById(Integer id) {
        Competition competition = getById(id);
        competition.setDeleteFlag(true);
        updateById(competition);
        divisionService.deleteByCompetition(id);
    }



}
