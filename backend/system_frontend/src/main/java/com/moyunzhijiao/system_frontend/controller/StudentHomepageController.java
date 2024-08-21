package com.moyunzhijiao.system_frontend.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_frontend.common.Result;
import com.moyunzhijiao.system_frontend.controller.dto.ArticleDTO;
import com.moyunzhijiao.system_frontend.controller.dto.CompetitionDTO;
import com.moyunzhijiao.system_frontend.controller.dto.VideoDTO;
import com.moyunzhijiao.system_frontend.service.StudentHomepageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "学生接口")
@RequestMapping("/cieps")
public class StudentHomepageController {
    @Autowired
    StudentHomepageService studentHomepageService;

    @GetMapping("/recommend")
    public Result getRecommend(@RequestHeader("authorization") String token,
                               @RequestParam Integer currentPage,
                               @RequestParam Integer pageSize){
        DecodedJWT jwt = JWT.decode(token);
        IPage<ArticleDTO> page=studentHomepageService.getRecommend(currentPage,pageSize);
        return Result.success(page);
    }
    @GetMapping("/biography")
    public Result getBiography(@RequestHeader("authorization") String token,
                               @RequestParam Integer currentPage,
                               @RequestParam Integer pageSize){
        DecodedJWT jwt = JWT.decode(token);
        IPage<ArticleDTO> page=studentHomepageService.getBiography(currentPage,pageSize);
        return Result.success(page);
    }

    @GetMapping("/master")
    public Result getMaster(@RequestHeader("authorization") String token,
                               @RequestParam Integer currentPage,
                               @RequestParam Integer pageSize){
        DecodedJWT jwt = JWT.decode(token);
        IPage<ArticleDTO> page=studentHomepageService.getMaster(currentPage,pageSize);
        return Result.success(page);
    }

    @GetMapping("/character")
    public Result getCharacter(@RequestHeader("authorization") String token,
                            @RequestParam Integer currentPage,
                            @RequestParam Integer pageSize){
        DecodedJWT jwt = JWT.decode(token);
        IPage<ArticleDTO> page=studentHomepageService.getCharacter(currentPage,pageSize);
        return Result.success(page);
    }

    @GetMapping("/culture")
    public Result getCulture(@RequestHeader("authorization") String token,
                               @RequestParam Integer currentPage,
                               @RequestParam Integer pageSize){
        DecodedJWT jwt = JWT.decode(token);
        IPage<ArticleDTO> page=studentHomepageService.getCulture(currentPage,pageSize);
        return Result.success(page);
    }

    @GetMapping("/all-article")
    public Result getAllArticle(@RequestHeader("authorization") String token,
                             @RequestParam Integer currentPage,
                             @RequestParam Integer pageSize,
                             @RequestParam String name){
        DecodedJWT jwt = JWT.decode(token);
        IPage<ArticleDTO> page=studentHomepageService.getAllArticle(currentPage,pageSize,name);
        return Result.success(page);
    }

    @GetMapping("/article-detail")
    public Result getArticleDetail(@RequestHeader("authorization") String token,
                                @RequestParam String id){
        DecodedJWT jwt = JWT.decode(token);
        ArticleDTO articleDTO=studentHomepageService.getArticleDetail(id);
        return Result.success(articleDTO);
    }

    @GetMapping("/video")
    public Result getVideo(@RequestHeader("authorization") String token,
                                @RequestParam Integer currentPage,
                                @RequestParam Integer pageSize,
                                @RequestParam String name){
        DecodedJWT jwt = JWT.decode(token);
        IPage<VideoDTO> page=studentHomepageService.getVideo(currentPage,pageSize,name);
        return Result.success(page);
    }

    @GetMapping("/video-detail")
    public Result getVideoDetail(@RequestHeader("authorization") String token,
                                   @RequestParam String id){
        DecodedJWT jwt = JWT.decode(token);
        VideoDTO videoDTO=studentHomepageService.getVideoDetail(id);
        return Result.success(videoDTO);
    }

    @GetMapping("/self-competition")
    public Result getSelfCompetition(@RequestHeader("authorization") String token,
                             @RequestParam Integer currentPage,
                             @RequestParam Integer pageSize){
        DecodedJWT jwt = JWT.decode(token);
        IPage<CompetitionDTO> page=studentHomepageService.getSelfCompetition(currentPage,pageSize);
        return Result.success(page);
    }

    @GetMapping("/competition-detail")
    public Result getCompetitionDetail(@RequestHeader("authorization") String token,
                                 @RequestParam String id){
        DecodedJWT jwt = JWT.decode(token);
        CompetitionDTO competitionDTO=studentHomepageService.getCompetitionDetail(id);
        return Result.success(competitionDTO);
    }
}
