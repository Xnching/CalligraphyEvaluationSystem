package com.moyunzhijiao.system_frontend.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_frontend.common.Result;
import com.moyunzhijiao.system_frontend.controller.dto.ArticleDTO;
import com.moyunzhijiao.system_frontend.controller.dto.CompetitionDTO;
import com.moyunzhijiao.system_frontend.service.competition.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompetitionController {
    @Autowired
    CompetitionService competitionService;

    @GetMapping("/cieps/existing-competition")
    public Result getExistingCompetition (@RequestHeader("authorization") String token,
                                          @RequestParam Integer currentPage,
                                          @RequestParam Integer pageSize){
        DecodedJWT jwt = JWT.decode(token);
        IPage<CompetitionDTO> page=competitionService.getExistingCompetition(currentPage,pageSize);
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
}
