package com.moyunzhijiao.system_frontend.service.competition;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.controller.dto.CompetitionDTO;
import com.moyunzhijiao.system_frontend.controller.dto.DivisionDTO;
import com.moyunzhijiao.system_frontend.controller.dto.ParticipantDTO;
import com.moyunzhijiao.system_frontend.entity.competition.Competition;
import com.moyunzhijiao.system_frontend.entity.competition.Division;
import com.moyunzhijiao.system_frontend.entity.competition.Participant;
import com.moyunzhijiao.system_frontend.mapper.Competition.CompetitionMapper;
import com.moyunzhijiao.system_frontend.mapper.Competition.DivisonMapper;
import com.moyunzhijiao.system_frontend.mapper.Competition.ParticipantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompetitionService extends ServiceImpl<CompetitionMapper, Competition> {
    @Autowired
    CompetitionMapper competitionMapper;

    @Autowired
    DivisonMapper divisonMapper;

    @Autowired
    ParticipantMapper participantMapper;

    public IPage<CompetitionDTO> getExistingCompetition(Integer currentPage, Integer pageSize,Integer stuId){
        IPage<CompetitionDTO> page =new Page<>(currentPage,pageSize);
        Integer total;
        page= competitionMapper.getExistingCompetition(page,stuId);
        total= competitionMapper.countExistingCompetition();
        page.setTotal(total);
        return page;
    }

    public IPage<CompetitionDTO> getHistoryCompetition(Integer currentPage, Integer pageSize,Integer id){
        IPage<CompetitionDTO> page =new Page<>(currentPage,pageSize);
        Integer total;
        page= competitionMapper.getHistoryCompetition(page,id);
        total= competitionMapper.countHistoryCompetition();
        page.setTotal(total);
        return page;
    }

    public CompetitionDTO getDivision(Integer id){
        QueryWrapper<Division> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("competition_id", id)
                .eq("delete_flag", 0)
                .select("id", "name"); // 只查询 id 和 name 字段

        // 使用 MyBatis Plus 进行查询
        List<Division> divisions = divisonMapper.selectList(queryWrapper);

        // 将查询结果转换为 DivisionDTO 列表
        List<DivisionDTO> division= divisions.stream()
                .map(d -> new DivisionDTO(d.getId(), d.getName()))
                .collect(Collectors.toList());
        CompetitionDTO competitionDTO = new CompetitionDTO();
        competitionDTO.setDivision(division);
        competitionDTO.setId(id.toString());
        competitionDTO.setName(competitionMapper.getDetail(id.toString()).getName());
        return competitionDTO;
    }

    public void applyCompetition(ParticipantDTO participantDTO){
        QueryWrapper<Participant> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("division_id", participantDTO.getDivision_id())
                .eq("student_id", participantDTO.getStudent_id())
                .eq("competition_id", participantDTO.getCompetition_id());

        long count = participantMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new RuntimeException("您已报名该比赛！"); // 或其他处理方式
        }

        // 创建 Participtant 实体类
        Participant participant = new Participant();
        participant.setDivision_id(participantDTO.getDivision_id());
        participant.setStudent_id(participantDTO.getStudent_id());
        participant.setCompetition_id(participantDTO.getCompetition_id());
        participant.setSubmission_id(participantDTO.getSubmission_id()); // 如果 submission_id 允许为空，可以不设置

        // 使用 MyBatis Plus 插入数据
        participantMapper.insert(participant);
    }
}

