package com.moyunzhijiao.system_backend.controller.competition;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyunzhijiao.system_backend.common.Constants;
import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.controller.dto.competition.ReviewResultDTO;
import com.moyunzhijiao.system_backend.entiy.competition.CompetitionSubmissions;
import com.moyunzhijiao.system_backend.entiy.competition.Division;
import com.moyunzhijiao.system_backend.entiy.competition.FinalReview;
import com.moyunzhijiao.system_backend.service.competition.CompetitionSubmissionsService;
import com.moyunzhijiao.system_backend.service.competition.FinalRankService;
import com.moyunzhijiao.system_backend.service.competition.FinalReviewService;
import com.moyunzhijiao.system_backend.service.competition.ReviewersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/backend/review-work")
@PreAuthorize("hasAuthority('评委评分')")
public class ReviewWorkController {
    @Autowired
    ReviewersService reviewersService;
    @Autowired
    CompetitionSubmissionsService competitionSubmissionsService;
    @Autowired
    FinalRankService finalRankService;
    @Autowired
    FinalReviewService finalReviewService;
    /*
     * 根据一个教师的id获取该教师的要评阅的所有组别
     * */
    @GetMapping("/teacher")
    public Result getDivisionOfTeacher(@RequestHeader("token") String token){
        // 解码 token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer teacherId = Integer.valueOf(jwt.getAudience().get(0));
        String userType = jwt.getClaim("userType").asString();
        if(!userType.equals("教师")){
            return Result.error(Constants.CODE_400,"您不是评阅教师，您无法观看使用此界面！");
        }
        List<Division> list = reviewersService.getDivisionOfTeacher(teacherId);
        return Result.success(list);
    }

    /*
    * 给一个教师获取一个组别的
    * */
    @GetMapping("/initial-page")
    public Result findPageOfInitial(@RequestHeader("token") String token, @RequestParam Integer divisionId, @RequestParam Integer pageSize){
        // 解码 token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer teacherId = Integer.valueOf(jwt.getAudience().get(0));
        List<CompetitionSubmissions> list = competitionSubmissionsService.getInitialToReview(teacherId,divisionId,pageSize);
        return Result.success(list);
    }

    /*
    * 获取进入最终评阅阶段的作品进行评阅
    * */
    @GetMapping("/final-page")
    public Result findPageOfFinal( @RequestParam Integer divisionId, @RequestParam Integer pageSize,@RequestParam Integer pageNum){
        IPage<CompetitionSubmissions> page = new Page<>(pageNum,pageSize);
        page = finalRankService.getFinalToReview(page,divisionId);
        return Result.success(page);
    }

    /*
    * 一个教师评阅一批初级作品
    * */
    @PutMapping("/initial-review")
    public Result initialReview(@RequestHeader("token") String token,@RequestBody List<ReviewResultDTO> list){
        // 解码 token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer teacherId = Integer.valueOf(jwt.getAudience().get(0));
        List<CompetitionSubmissions> competitionSubmissionsList = list.stream()
                .map(result -> {
                    CompetitionSubmissions competitionSubmissions = competitionSubmissionsService.getById(result.getId());
                    if(result.getMineScore()==null){
                        competitionSubmissions.setTeacherId(null);
                    }else {
                        competitionSubmissions.setTeacherId(teacherId);
                    }
                    competitionSubmissions.setInitialScore(result.getMineScore());
                    competitionSubmissions.setInitialEvaluation(result.getMineEvaluation());
                    return competitionSubmissions;
                })
                .collect(Collectors.toList());
        competitionSubmissionsService.saveOrUpdateBatch(competitionSubmissionsList);
        return Result.success();
    }


    /*
     * 一个教师评阅一个最终作品
     * */
    @PostMapping("/final-review")
    public Result finalReview(@RequestHeader("token") String token,@RequestBody Map<String,Integer> params){
        // 解码 token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer teacherId = Integer.valueOf(jwt.getAudience().get(0));
        Integer score = params.get("score");
        Integer submissionId = params.get("submissionId");
        FinalReview finalReview = new FinalReview();
        finalReview.setScore(score);
        finalReview.setTeacherId(teacherId);
        finalReview.setSubmissionId(submissionId);
        finalReviewService.save(finalReview);
        return Result.success();
    }

    /*
    * 获取是否在一个组别中是否被催促
    * */
    @GetMapping("/get-urge")
    public Result getIsUrged(@RequestHeader("token") String token,@RequestParam Integer divisionId){
        // 解码 token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer teacherId = Integer.valueOf(jwt.getAudience().get(0));
        boolean isUrged = reviewersService.getIsUrged(teacherId,divisionId);
        return Result.success(isUrged);
    }
}
