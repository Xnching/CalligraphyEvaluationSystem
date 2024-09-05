package com.moyunzhijiao.system_frontend.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.moyunzhijiao.system_frontend.common.Result;
import com.moyunzhijiao.system_frontend.controller.dto.CompetitionDetailDTO;
import com.moyunzhijiao.system_frontend.service.competition.CompetitionSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompetitionSubmissionController {
    @Autowired
    CompetitionSubmissionService competitionSubmissionService;

    @GetMapping("/cieps/competition-submission-detail")
    public Result getCompetitionDetail(@RequestHeader("authorization") String token,
                                       @RequestParam Integer comId,
                                       @RequestParam Integer stuNo){
        DecodedJWT jwt = JWT.decode(token);
        CompetitionDetailDTO competitionDetailDTO=competitionSubmissionService.getCompetitionDetail(comId,stuNo);
        return Result.success(competitionDetailDTO);
    }

    @GetMapping("/cieps/outstanding-competition-submission-detail")
    public Result getCompetitionDetail(@RequestHeader("authorization") String token,
                                       @RequestParam Integer submissionId){
        DecodedJWT jwt = JWT.decode(token);
        CompetitionDetailDTO competitionDetailDTO=competitionSubmissionService.getOutstandingDetail(submissionId);
        return Result.success(competitionDetailDTO);
    }
}
