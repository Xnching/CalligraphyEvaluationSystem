package com.moyunzhijiao.system_app.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.moyunzhijiao.system_app.common.Constants;
import com.moyunzhijiao.system_app.common.Result;
import com.moyunzhijiao.system_app.controller.dto.SignUpDTO;
import com.moyunzhijiao.system_app.controller.dto.SubmitDTO;
import com.moyunzhijiao.system_app.controller.dto.fonted.competition.CompetitionDetailInfo;
import com.moyunzhijiao.system_app.controller.dto.fonted.competition.CompetitionInfo;
import com.moyunzhijiao.system_app.service.ConfigService;
import com.moyunzhijiao.system_app.service.competition.CompetitionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.scanner.Constant;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Tag(name = "竞赛接口")
@RequestMapping("/CompetitionService")
public class CompetitionController {
    @Autowired
    CompetitionService competitionService;


    @Operation(summary = "获取竞赛信息（可提交的竞赛而非信息展示）")
    @GetMapping("/getCompetition")
    public Result getCompetition(@RequestHeader("Authorization")String token, @RequestParam("type")String type){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));

        System.out.println("获取竞赛信息"+userId+type);

        List<CompetitionInfo> competitionInfos = competitionService.getCompetition(userId, type);

        return Result.success(competitionInfos);
    }


    @Operation(summary = "获取竞赛详细信息（可提交的竞赛而非信息展示）")
    @GetMapping("/getCompetitionDetail/{exerciseId}")
    public Result getCompetitionDetail(@RequestHeader("Authorization")String token, @PathVariable("exerciseId")Integer exerciseId){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));

        System.out.println("获取竞赛详细信息"+userId+exerciseId);

        CompetitionDetailInfo competitionDetailInfo = competitionService.getCompetitionDetail(userId, exerciseId);

        return Result.success(competitionDetailInfo);
    }


    @Operation(summary = "竞赛报名")
    @PostMapping("/signUpCompetition")
    public Result signUpCompetition(@RequestHeader("Authorization")String token, @RequestBody SignUpDTO signUpDTO){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        signUpDTO.setUserId(userId);
        System.out.println("竞赛报名"+signUpDTO);

        Boolean ifSuccess = competitionService.signUpCompetition(signUpDTO);

        return Result.success(ifSuccess);
    }


    //提交竞赛作品
    @Operation(summary = "竞赛提交")
    @PostMapping("/submitCompetition/{competitionId}")
    public Result submitCompetition(@RequestPart("image") MultipartFile[] files, @RequestHeader("Authorization")String token, @PathVariable("competitionId") Integer competitionId){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));

        List<String> urls = new ArrayList<>(); // 用于保存所有文件的 URL
        try {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
                    String filePath = ConfigService.getCompetitionFilePath() + fileName;
                    String url = ConfigService.getCompetitionUrl() + "/" + fileName;
                    File dest = new File(filePath);

                    file.transferTo(dest); // 保存文件
                    System.out.println("文件保存路径: " + filePath);

                    urls.add(url); // 保存文件 URL
                } else {
                    return Result.error(Constants.CODE_400, "文件为空");
                }
            }

            SubmitDTO submitDTO = new SubmitDTO(userId, competitionId, urls.toArray(new String[0])); // 将 URL 列表转换为数组
            System.out.println("竞赛提交: " + submitDTO);

             Boolean ifSuccess = competitionService.submitCompetition(submitDTO);
//            Boolean ifSuccess = true; // 假设所有文件上传成功
            return Result.success(ifSuccess);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error(Constants.CODE_401, "文件上传失败");
        }


    }
}
