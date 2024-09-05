package com.moyunzhijiao.system_backend.controller.competition;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyunzhijiao.system_backend.common.Constants;
import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.controller.dto.front.TeacherDTO;
import com.moyunzhijiao.system_backend.entiy.competition.*;
import com.moyunzhijiao.system_backend.entiy.front.Teacher;
import com.moyunzhijiao.system_backend.service.competition.*;
import com.moyunzhijiao.system_backend.service.front.NoteService;
import com.moyunzhijiao.system_backend.service.front.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/backend/review-management")
public class ReviewManagementController {
    @Autowired
    CompetitionRulesService competitionRulesService;
    @Autowired
    DivisionService divisionService;
    @Autowired
    ReviewersService reviewersService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    CompetitionSubmissionsService competitionSubmissionsService;
    @Autowired
    FinalRankService finalRankService;
    @Autowired
    CompetitionService competitionService;
    @Autowired
    NoteService noteService;
    /*
    * 修改规则
    * */
    @PutMapping("/update-rules")
    @Transactional
    public Result updateRules(@RequestBody Map<String, Integer> params) {
        List<CompetitionRules> rulesList = new ArrayList<>();
        AtomicInteger i = new AtomicInteger(1);
        params.forEach((key, value) -> {
            CompetitionRules rule = new CompetitionRules();
            rule.setId(i.getAndIncrement());
            rule.setQuestion(key);
            rule.setAnswer(value);
            rulesList.add(rule);
        });
        System.out.println(rulesList);
        competitionRulesService.saveOrUpdateBatch(rulesList);
        return Result.success();
    }

    /*
    * 获取竞赛规则
    * */
    @GetMapping("/get-rules")
    public Result getRules(){
        List<CompetitionRules> list = competitionRulesService.list();
        return Result.success(list);
    }

    /*
    * 获取进行中的竞赛组别列表,即状态不是准备报名中和已结束的竞赛
    * */
    @GetMapping("/division-ing")
    public Result getIngDivision(){
        List<Division> divisions = divisionService.getIngList();
        divisions.forEach(division -> {
            if(division.getState().equals("同竞赛状态")){
                Competition competition = competitionService.getById(division.getCompetitionId());
                division.setState(competition.getState());
            }
        });
        return Result.success(divisions);
    }

    /*
    * 获取所有参与评阅的教师
    * */
    @GetMapping("/all-ing-teacher")
    public Result getAllReviewingTeacher(@RequestParam Integer pageNum, @RequestParam Integer pageSize
            ,@RequestParam(defaultValue = "")String str){
        IPage<TeacherDTO> page = new Page<>(pageNum,pageSize);
        page = reviewersService.getAllReviewingTeacher(page,str);
        return Result.success(page);
    }

    /*
    * 获取所有教师，用于添加评阅教师
    * */
    @GetMapping("/all-teacher")
    public Result getAllTeacher(@RequestParam Integer pageNum, @RequestParam Integer pageSize
            ,@RequestParam(defaultValue = "")String str){
        IPage<TeacherDTO> page = new Page<>(pageNum,pageSize);
        page = teacherService.getAllTeacher(page,str);
        return Result.success(page);
    }

    /*
    * 添加评阅教师到对应的竞赛中
    * */
    @PostMapping("/add-teacher")
    @Transactional
    public Result addReviewingTeacher(@RequestBody Map<String, List<Integer>> payload){
        List<Integer> divisionIds = payload.get("divisionIds");
        List<Integer> teacherIds = payload.get("teacherIds");
        List<Reviewers> reviewersList = new ArrayList<>();
        for(Integer divisionId:divisionIds){
            for (Integer teacherId : teacherIds){
                Reviewers reviewers = new Reviewers();
                reviewers.setDivisionId(divisionId);
                reviewers.setTeacherId(teacherId);
                reviewersList.add(reviewers);
            }
        }
        reviewersService.saveBatch(reviewersList);
        return Result.success();
    }

    /*
    * 催促一个教师
    * */
    @PutMapping("/urge-teacher")
    public Result urgeTeacherAllDivision(@RequestBody Map<String, Integer> params){
        Integer teacherId = params.get("teacherId");
        reviewersService.urgeTeacherAllDivision(teacherId);
        return Result.success();
    }


    /*
     * 催促一个组别
     * */
    @PutMapping("/urge-division")
    public Result urgeDivisionAllTeacher(@RequestBody Map<String, Integer> params){
        Integer divisionId = params.get("divisionId");
        reviewersService.urgeDivisionAllTeacher(divisionId);
        return Result.success();
    }

    /*
    * 删除一个教师的所有评阅组别记录
    * */
    @DeleteMapping("/delete-teacher-all-division")
    public Result deleteTeacherAllDivision(@RequestBody Map<String,String> r){
        Integer id = Integer.valueOf(r.get("id"));
        reviewersService.deleteTeacher(id);
        return Result.success();
    }

    /*
    * 一个组别的所有评阅教师
    * */
    @GetMapping("/teacher-in-division")
    public Result getTeacherInDivision(@RequestParam Integer pageNum, @RequestParam Integer pageSize
            ,@RequestParam(defaultValue = "")String str,@RequestParam Integer divisionId){
        IPage<TeacherDTO> page = new Page<>(pageNum,pageSize);
        page = reviewersService.getReviewingTeacherInDivision(page,str,divisionId);
        return Result.success(page);
    }

    /*
     * 获取所有教师，用于添加评阅教师到一个组别中
     * */
    @GetMapping("/all-teacher-in-division")
    public Result getAllTeacherInDivision(@RequestParam Integer pageNum, @RequestParam Integer pageSize
            ,@RequestParam(defaultValue = "")String str,@RequestParam Integer divisionId){
        IPage<TeacherDTO> page = new Page<>(pageNum,pageSize);
        page = teacherService.getAllTeacherInDivision(page,str,divisionId);
        return Result.success(page);
    }

    /*
    * 把一个教师从评阅组中删除出去
    * */
    @DeleteMapping("/delete-teacher-in-division")
    public Result deleteTeacherInDivision(@RequestBody Map<String,Integer> r){
        Integer divisionId = r.get("divisionId");
        Integer teacherId = r.get("teacherId");
        reviewersService.deleteTeacherInDivision(divisionId,teacherId);
        return Result.success();
    }

    /*
     * 添加评阅教师到一个组别中
     * */
    @PostMapping("/add-teacher-in-division")
    @Transactional
    public Result addReviewingTeacherInDivision(@RequestBody Map<String, List<Integer>> payload){
        List<Integer> divisionIds = payload.get("divisionIds");
        Integer divisionId = divisionIds.get(0);
        List<Integer> teacherIds = payload.get("teacherIds");
        List<Reviewers> reviewersList = new ArrayList<>();
        for (Integer teacherId : teacherIds){
            Reviewers reviewers = new Reviewers();
            reviewers.setDivisionId(divisionId);
            reviewers.setTeacherId(teacherId);
            reviewersList.add(reviewers);
        }
        reviewersService.saveBatch(reviewersList);
        return Result.success();
    }

    /*
     * 催促一个教师
     * */
    @PutMapping("/urge-teacher-in-division")
    public Result urgeTeacherInDivision(@RequestBody Map<String, Integer> params){
        Integer teacherId = params.get("teacherId");
        Integer divisionId = params.get("divisionId");
        reviewersService.urgeTeacherInDivision(teacherId,divisionId);
        return Result.success();
    }

    /*
    * 使一个组别进入最终评阅阶段
    * */
    @PutMapping("/become-final")
    @Transactional
    public Result becomeFinal(@RequestBody Map<String, Integer> params){
        Integer divisionId = params.get("divisionId");
        Division division = divisionService.getById(divisionId);
        if(division.getState().equals("最终评阅中")){
            return Result.error(Constants.CODE_401,"该竞赛已进入最终评阅阶段！");
        }
        //获取进入最终评阅阶段的百分比
        Integer ration = competitionRulesService.getByField("ration");
        BigDecimal percentage = BigDecimal.valueOf(ration*0.01);
        //更新排名并获取进入最终评阅阶段的作品的id
        List<Integer> submissionIdList = competitionSubmissionsService.updateInitialRank(divisionId, percentage);
        //进入最终评阅阶段的作品的id更新到fina_rank表里表示进入最终评阅阶段了
        List<FinalRank> finalRanks = submissionIdList.stream().map(submissionId->{
            FinalRank finalRank = new FinalRank(submissionId);
            finalRank.setDivisionId(divisionId);
            return finalRank;
        }).collect(Collectors.toList());
        finalRankService.saveBatch(finalRanks);
        //接着组别更新状态
        division.setState("最终评阅中");
        divisionService.updateById(division);
        return Result.success();
    }

    /*
     * 使一个组别结束最终评阅阶段
     * */
    @PutMapping("/end-final")
    @Transactional
    public Result endFinal(@RequestBody Map<String, Integer> params){
        Integer divisionId = params.get("divisionId");
        Division division = divisionService.getById(divisionId);
        if(division.getState().equals("已结束")){
            return Result.error(Constants.CODE_401,"该竞赛已结束最终评阅阶段！");
        }
        finalRankService.updateScoreAndRanks(division);
        //接着组别更新状态
        division.setState("已结束");
        divisionService.updateById(division);
        //检查该竞赛下面的所有组别是否都是已结束，如果都是已结束就让竞赛也进入已结束状态
        Integer competitionId = division.getCompetitionId();
        boolean can = divisionService.canCompetitionToEnd(competitionId);
        if(can){
            Competition competition = competitionService.getById(competitionId);
            competition.setState("已结束");
            competitionService.updateById(competition);
            noteService.endCompetition(competition);
        }
        return Result.success();
    }
}
