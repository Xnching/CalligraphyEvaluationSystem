package com.moyunzhijiao.system_backend.controller.competition;

import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyunzhijiao.system_backend.common.Constants;
import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.controller.dto.front.StudentDTO;
import com.moyunzhijiao.system_backend.entiy.competition.Competition;
import com.moyunzhijiao.system_backend.entiy.competition.CompetitionRequirements;
import com.moyunzhijiao.system_backend.entiy.competition.CompetitionSubmissions;
import com.moyunzhijiao.system_backend.entiy.competition.Division;
import com.moyunzhijiao.system_backend.entiy.front.Student;
import com.moyunzhijiao.system_backend.service.ConfigService;
import com.moyunzhijiao.system_backend.service.competition.*;
import com.moyunzhijiao.system_backend.service.front.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/backend/competition")
@PreAuthorize("hasAuthority('竞赛列表')")
public class CompetitionController {

    @Autowired
    CompetitionService competitionService;
    @Autowired
    DivisionService divisionService;
    @Autowired
    CompetitionRequirementsService competitionRequirementsService;
    @Autowired
    ParticipantService participantService;
    @Autowired
    CompetitionSubmissionsService competitionSubmissionsService;
    @Autowired
    NoteService noteService;
    /*
    * 竞赛列表
    * */
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,@RequestParam(defaultValue = "")String str){
        IPage<Competition> page = new Page<>(pageNum,pageSize);
        page = competitionService.selectPage(page,str);
        return Result.success(page);
    }

    /*
    * 根据竞赛id获取组别信息
    * */
    @GetMapping("/detail")
    public Result getDetailOfCompetition(@RequestParam Integer id){
        List<Division> list = divisionService.getByCompetition(id);
        return Result.success(list);
    }

    /*
    * 逻辑删除一个竞赛
    * */
    @PutMapping("/delete")
    public Result deleteCompetition(@RequestBody Map<String,String> r){
        Integer id = Integer.valueOf(r.get("id"));
        competitionService.deleteById(id);
        return Result.success();
    }
    /*
    * 添加一个竞赛以及对应组别
    * */
    @PostMapping("/add")
    @Transactional
    public Result addCompetition(@RequestPart("competition") String competitionStr,@RequestPart("file") MultipartFile file){
        //先保存文件
        if(!file.isEmpty()){
            String fileName = UUID.randomUUID() + "-" +file.getOriginalFilename();
            // 构造文件的路径
            String filePath = ConfigService.getCompetitionFilePath() + fileName;
            String url = ConfigService.getCompetitionUrl()+"/"+fileName;
            File dest = new File(filePath);
            try {
                file.transferTo(dest);
                //接着保存数据
                Competition competition = JSONUtil.toBean(competitionStr, Competition.class);
                competition.setPicture(url);
                CompetitionRequirements competitionRequirements = new CompetitionRequirements();
                //保存竞赛
                competitionService.save(competition);
                noteService.addCompetition(competition);
                competitionRequirements.setCompetitionId(competition.getId());
                competitionRequirements.setRequirements(competition.getRequirement());
                //保存要求
                competitionRequirementsService.save(competitionRequirements);
                for (Division division: competition.getGroups()) {
                    division.setCompetitionId(competition.getId());
                }
                //保存组别
                divisionService.saveBatchAndRequirement(competition.getGroups());
            } catch (IOException e) {
                e.printStackTrace();
                return Result.error(Constants.CODE_401,"文件上传失败");
            }
        } else {
            return Result.error(Constants.CODE_400,"文件为空");
        }
        return Result.success();
    }

    /*
    * 一个竞赛的详情
    * */
    @GetMapping("/detailed-competition")
    public Result getCompetitionDetail(@RequestParam Integer competitionId){
        Competition competition = competitionService.getById(competitionId);
        CompetitionRequirements re = competitionRequirementsService.getById(competitionId);
        competition.setRequirement(re.getRequirements());
        List<Division> divisions = divisionService.getByCompetition(competitionId);
        competition.setGroups(divisions);
        return Result.success(competition);
    }

    /*
    * 获取一个竞赛的参赛人员信息
    * */
    @GetMapping("/participant")
    public Result getParticipant(@RequestParam Integer pageNum, @RequestParam Integer pageSize,@RequestParam(defaultValue = "")String str
            ,@RequestParam Integer competitionId,@RequestParam(required = false) Integer gradeId,@RequestParam(required = false) Integer status){
        IPage<StudentDTO> page = new Page<>(pageNum,pageSize);
        page = participantService.getParticipantByCompetition(page,str,competitionId,gradeId,status);
        return Result.success(page);
    }


    /*
     * 获取一个竞赛的参赛作品信息
     * */
    @GetMapping("/submissions")
    public Result getSubmissions(@RequestParam Integer pageNum, @RequestParam Integer pageSize,@RequestParam(defaultValue = "")String str,
                                 @RequestParam Integer competitionId,@RequestParam(required = false) Integer gradeId,@RequestParam(defaultValue = "") String level){
        IPage<CompetitionSubmissions> page = new Page<>(pageNum,pageSize);
        if(level.isEmpty()){
            level=null;
        }
        page = participantService.getSubmissionByCompetition(page,str,competitionId,gradeId,level);
        return Result.success(page);
    }

    /*
    * 修改竞赛信息
    * */
    @PutMapping("/update")
    public Result updateCompetition(@RequestBody Competition competition){
        CompetitionRequirements competitionRequirements = competitionRequirementsService.getById(competition.getId());
        competitionRequirements.setRequirements(competition.getRequirement());
        List<Division> divisions = competition.getGroups();
        divisions.forEach(division -> division.setCompetitionId(competition.getId()));
        competitionService.updateById(competition);
        competitionRequirementsService.updateById(competitionRequirements);
        divisionService.saveOrUpdateBatch(divisions);
        return Result.success();
    }

    /*
    * 根据作品id获取一个竞赛作品的详细信息，在前端的作品信息页面
    * */
    @GetMapping("/submission-detail-submission")
    public Result getSubmissionDetailById(@RequestParam Integer submissionId){
        CompetitionSubmissions competitionSubmissions = competitionSubmissionsService.getBySubmission(submissionId);
        return Result.success(competitionSubmissions);
    }


    /*
     * 根据学生和竞赛信息获取一个竞赛作品的详细信息，在前端的学生信息页面
     * */
    @GetMapping("/submission-detail-student")
    public Result getSubmissionDetailById(@RequestParam Integer studentId,@RequestParam Integer competitionId){
        Integer submissionId = participantService.getSubmissionId(studentId,competitionId);
        if(submissionId==0){
            return Result.error(Constants.CODE_400,"该学生还未完成作品提交！");
        }
        CompetitionSubmissions competitionSubmissions = competitionSubmissionsService.getBySubmission(submissionId);
        return Result.success(competitionSubmissions);
    }


    /*
    * 获取竞赛组别名称
    * */
    @GetMapping("/competition-name")
    public Result getCompetitionName(@RequestParam Integer divisionId){
        String competitionDivisionName = "";
        Division division = divisionService.getById(divisionId);
        Competition competition = competitionService.getById(division.getCompetitionId());
        competitionDivisionName=competition.getName()+"-"+division.getName();
        return Result.success(competitionDivisionName);
    }
}
