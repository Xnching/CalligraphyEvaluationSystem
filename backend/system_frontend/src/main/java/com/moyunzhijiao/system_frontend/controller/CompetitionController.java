package com.moyunzhijiao.system_frontend.controller;

import cn.hutool.core.util.ObjectUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_frontend.common.Constants;
import com.moyunzhijiao.system_frontend.common.Result;
import com.moyunzhijiao.system_frontend.controller.dto.CompetitionDTO;
import com.moyunzhijiao.system_frontend.controller.dto.CompetitionDetailDTO;
import com.moyunzhijiao.system_frontend.controller.dto.ParticipantDTO;
import com.moyunzhijiao.system_frontend.controller.dto.StudentDTO;
import com.moyunzhijiao.system_frontend.entity.Announcement;
import com.moyunzhijiao.system_frontend.entity.AnnouncementContent;
import com.moyunzhijiao.system_frontend.entity.competition.Competition;
import com.moyunzhijiao.system_frontend.entity.competition.CompetitionRequirements;
import com.moyunzhijiao.system_frontend.mapper.Competition.CompetitionRequirementsMapper;
import com.moyunzhijiao.system_frontend.service.AnnouncementContentService;
import com.moyunzhijiao.system_frontend.service.AnnouncementService;
import com.moyunzhijiao.system_frontend.service.KlassService;
import com.moyunzhijiao.system_frontend.service.StudentService;
import com.moyunzhijiao.system_frontend.service.competition.CompetitionService;
import com.moyunzhijiao.system_frontend.service.competition.CompetitionSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class CompetitionController {
    @Autowired
    CompetitionService competitionService;
    @Autowired
    CompetitionSubmissionService competitionSubmissionService;
    @Autowired
    KlassService klassService;
    @Autowired
    StudentService studentService;
    @Autowired
    CompetitionRequirementsMapper competitionRequirementsMapper;
    @Autowired
    AnnouncementService announcementService;
    @Autowired
    AnnouncementContentService announcementContentService;

    @GetMapping("/cieps/existing-competition")
    public Result getExistingCompetition (@RequestHeader("authorization") String token,
                                          @RequestParam Integer currentPage,
                                          @RequestParam Integer pageSize){
        DecodedJWT jwt = JWT.decode(token);
        Integer stuId = Integer.valueOf(jwt.getAudience().get(0));
        IPage<CompetitionDTO> page=competitionService.getExistingCompetition(currentPage,pageSize,stuId);
        return Result.success(page);
    }

    @GetMapping("/cieps/history-competition")
    public Result getHistoryCompetition (@RequestHeader("authorization") String token,
                                          @RequestParam Integer currentPage,
                                          @RequestParam Integer pageSize){
        DecodedJWT jwt = JWT.decode(token);
        Integer stuId = Integer.valueOf(jwt.getAudience().get(0));
        IPage<CompetitionDTO> page=competitionService.getHistoryCompetition(currentPage,pageSize,stuId);
        return Result.success(page);
    }

    @GetMapping("/cieps/division")
    public Result getDivision (@RequestHeader("authorization") String token,
                               @RequestParam Integer comId){
        DecodedJWT jwt = JWT.decode(token);
        CompetitionDTO competitionDTO=competitionService.getDivision(comId);
        return Result.success(competitionDTO);
    }

    @PostMapping("/cieps/apply-competition")
    public Result applyCompetition(@RequestHeader("authorization") String token,
                                   @RequestBody ParticipantDTO participantDTO){
        DecodedJWT jwt = JWT.decode(token);
        Integer student_id = Integer.valueOf(jwt.getAudience().get(0));
        participantDTO.setStudent_id(student_id);
        competitionService.applyCompetition(participantDTO,false);
        return Result.success();
    }

    /*
    * 获取所有竞赛的列表
    * */
    @GetMapping("/ciep/my-competition")
    public Result findAllList(){
        List<Competition> list = competitionService.getAllList();
        return Result.success(list);
    }

    /*
    * 教师获取关于一个竞赛组别的详情，其中包含各学生提交情况
    * */
    @GetMapping("/ciep/competition-detail")
    public Result findCompetitionDetail(@RequestHeader("authorization") String token,@RequestParam Integer competitionId){
        DecodedJWT jwt = JWT.decode(token);
        Integer teacherId = Integer.valueOf(jwt.getAudience().get(0));
        List<StudentDTO> list = competitionService.getCompetitionDetailOfTea(teacherId,competitionId);
        Map<String,Object> param = new HashMap<>();
        param.put("stuList",list);
        CompetitionRequirements competitionRequirements = competitionRequirementsMapper.selectById(competitionId);
        param.put("info",competitionRequirements.getRequirements());
        return Result.success(param);
    }

    /*
    * 获取一个竞赛作品的详情
    * */
    @GetMapping("/ciep/competition-work")
    public Result findSubmissionDetail(@RequestParam Integer divisionId,@RequestParam Integer stuId){
        CompetitionDetailDTO competitionDetailDTO = competitionSubmissionService.getCompetitionDetail(divisionId,stuId);
        return Result.success(competitionDetailDTO);
    }

    /*
    * 教师为一个班级的所有人报名参赛
    * */
    @PostMapping("/ciep/apply")
    public Result applyOfKlass(@RequestBody Map<String,Integer> params){
        Integer klassId = params.get("classId");
        Integer divisionId = params.get("divisionId");
        Integer competitionId = params.get("competitionId");
        // 创建 ArrayList 并添加 klassId
        List<Integer> klassIdList = new ArrayList<>();
        klassIdList.add(klassId);
        System.out.println(klassIdList);
        // 获取学生 ID 列表
        List<Integer> studentIdList = studentService.getByKlassList(klassIdList);
        System.out.println(studentIdList);
        ParticipantDTO participantDTO = new ParticipantDTO();
        //计数
        AtomicInteger total = new AtomicInteger();
        studentIdList.forEach(studentId->{
            participantDTO.setDivision_id(divisionId);
            participantDTO.setStudent_id(studentId);
            participantDTO.setCompetition_id(competitionId);
            total.addAndGet(competitionService.applyCompetition(participantDTO, true));
        });
        if(total.get()==0){
            return Result.error(Constants.CODE_400,"该班级已经全部报名！");
        }
        return Result.success();
    }

    @GetMapping("/ciep/other-competition")
    public Result findOtherCompetition(){
        List<Announcement> list = announcementService.findOtherCompetition();
        return Result.success(list);
    }

    @GetMapping("/ciep/other-competition-detail")
    public Result findOtherCompetition(@RequestParam Integer id){
        AnnouncementContent announcementContent = announcementContentService.getById(id);
        String content;
        if(ObjectUtil.isNull(announcementContent)){
            content = null;
        }else
            content = announcementContent.getMessage();
        return Result.success(content);
    }

}
