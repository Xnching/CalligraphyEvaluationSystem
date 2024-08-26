package com.moyunzhijiao.system_frontend.service.competition;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.controller.dto.CompetitionDTO;
import com.moyunzhijiao.system_frontend.controller.dto.DivisionDTO;
import com.moyunzhijiao.system_frontend.controller.dto.ParticipantDTO;
import com.moyunzhijiao.system_frontend.entity.Grade;
import com.moyunzhijiao.system_frontend.entity.Student;
import com.moyunzhijiao.system_frontend.entity.competition.Competition;
import com.moyunzhijiao.system_frontend.entity.competition.Division;
import com.moyunzhijiao.system_frontend.entity.competition.Participant;
import com.moyunzhijiao.system_frontend.exception.ServiceException;
import com.moyunzhijiao.system_frontend.mapper.Competition.CompetitionMapper;
import com.moyunzhijiao.system_frontend.mapper.Competition.DivisionMapper;
import com.moyunzhijiao.system_frontend.mapper.Competition.ParticipantMapper;
import com.moyunzhijiao.system_frontend.mapper.GradeMapper;
import com.moyunzhijiao.system_frontend.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompetitionService extends ServiceImpl<CompetitionMapper, Competition> {
    @Autowired
    CompetitionMapper competitionMapper;

    @Autowired
    DivisionMapper divisionMapper;

    @Autowired
    ParticipantMapper participantMapper;

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    GradeMapper gradeMapper;

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
        List<Division> divisions = divisionMapper.selectList(queryWrapper);

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
            throw new ServiceException(600,"您已报名该比赛！"); // 或其他处理方式
        }
        if (!isParticipantEligible(participantDTO)) {
            throw new ServiceException(600,"您不符合该比赛的参赛资格！");
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

    public boolean isParticipantEligible(ParticipantDTO participantDTO) {
        // 1. 获取 participantDTO 中的 student_id 和 division_id
        Integer studentId = participantDTO.getStudent_id();
        Integer divisionId = participantDTO.getDivision_id();

        // 2. 根据 student_id 查询 student 信息，获取 grade_id
        Student student = studentMapper.selectById(studentId);
        if (student == null) {
            throw new ServiceException(600,"学生不存在！");
        }
        Integer gradeId = student.getGradeId();

        // 3. 根据 division_id 查询 division 信息，获取 target
        Division division = divisionMapper.selectById(divisionId);
        if (division == null) {
            throw new ServiceException(600,"组别不存在！");
        }
        String target = division.getTarget();

        // 4. 根据 grade_id 查询 grade 信息，获取 name
        Grade grade = gradeMapper.selectById(gradeId);
        if (grade == null) {
            throw new ServiceException(600,"年级不存在！");
        }
        String gradeName = grade.getName();

        // 5. 判断是否符合参赛资格
        if ("小学和初中".equals(target)) {
            return true; // 小学和初中都可以参加
        } else if ("小学".equals(target)) {
            return gradeName.contains("小学"); // 判断年级名称是否包含"小学"
        } else if ("初中".equals(target)) {
            return gradeName.contains("初中"); // 判断年级名称是否包含"初中"
        } else {
            return false; // 其他情况，默认不符合资格
        }
    }
}

