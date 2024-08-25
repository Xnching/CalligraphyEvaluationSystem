package com.moyunzhijiao.system_backend.service.competition;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.controller.dto.front.StudentDTO;
import com.moyunzhijiao.system_backend.entiy.competition.CompetitionSubmissions;
import com.moyunzhijiao.system_backend.entiy.competition.Participant;
import com.moyunzhijiao.system_backend.mapper.competition.ParticipantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParticipantService extends ServiceImpl<ParticipantMapper, Participant> {
    @Autowired
    ParticipantMapper participantMapper;

    /*
     * 竞赛详情中的参赛人员列表
     * */
    public IPage<StudentDTO> getParticipantByCompetition(IPage<StudentDTO> page, String str, Integer competitionId, Integer gradeId, Integer status) {
        page = participantMapper.selectStudentByCompetition(page,str,competitionId,gradeId,status);
        long total = participantMapper.countStudentByCompetition(str,competitionId,gradeId,status);
        page.setTotal(total);
        return page;
    }

    public IPage<CompetitionSubmissions> getSubmissionByCompetition(IPage<CompetitionSubmissions> page, String str, Integer competitionId,Integer gradeId,String level) {
        page = participantMapper.selectSubmissionByCompetition(page,str,competitionId,gradeId,level);
        long total = participantMapper.countSubmissionByCompetition(str,competitionId,gradeId,level);
        page.setTotal(total);
        return page;
    }

    /*
    * 根据学生id和竞赛id查找到作品
    * */
    public Integer getSubmissionId(Integer studentId, Integer competitionId) {
        QueryWrapper<Participant> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id",studentId);
        queryWrapper.eq("competition_id",competitionId);
        Participant participant = participantMapper.selectOne(queryWrapper);
        return participant.getSubmissionId();
    }
}
