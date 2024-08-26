package com.moyunzhijiao.system_frontend.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.moyunzhijiao.system_frontend.common.Constants;
import com.moyunzhijiao.system_frontend.common.Result;
import com.moyunzhijiao.system_frontend.controller.dto.QAFDTO;
import com.moyunzhijiao.system_frontend.entity.help.Feedback;
import com.moyunzhijiao.system_frontend.service.Help.FeedbackService;
import com.moyunzhijiao.system_frontend.service.Help.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class QAFController {
    @Autowired
    QuestionService questionService;
    @Autowired
    FeedbackService feedbackService;

    @Operation(summary = "教师获取1.常见问题2.建议与反馈")
    @Parameters({
            @Parameter(name = "type",description = "请求类型",in = ParameterIn.PATH),
            @Parameter(name = "token",description = "请求token",required = true,in = ParameterIn.HEADER),
    })
    @GetMapping("/ciep/help-center")
    public Result findQAF(@RequestHeader("authorization") String token, @RequestParam String type){
        int t = Integer.valueOf(type);
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        String userType = jwt.getClaim("userType").asString();
        QAFDTO qafdto;
        switch (t){
            case 1:
                qafdto = questionService.getQuestions();
                break;
            case 2:
                qafdto = feedbackService.getFeedback(userType,userId);
                break;
            default:return Result.error(Constants.CODE_400,"错误的type类型！");
        }
        return Result.success(qafdto);
    }

    @Operation(summary = "教师新增反馈")
    @PostMapping("/ciep/feedback")
    public Result addFeedback(@RequestHeader("authorization") String token,@RequestBody Feedback feedback){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        String userType = jwt.getClaim("userType").asString();
        feedback.setProviderType(userType);
        feedback.setProviderId(userId);
        feedbackService.insertFeedback(feedback);
        return Result.success();
    }

    @GetMapping("/cieps/help-center")
    public Result findQAFofStu(@RequestHeader("authorization") String token, @RequestParam String type){
        int t = Integer.valueOf(type);
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        String userType = jwt.getClaim("userType").asString();
        QAFDTO qafdto;
        switch (t){
            case 1:
                qafdto = questionService.getQuestions();
                break;
            case 2:
                qafdto = feedbackService.getFeedback(userType,userId);
                break;
            default:return Result.error(Constants.CODE_400,"错误的type类型！");
        }
        return Result.success(qafdto);
    }

    @Operation(summary = "学生新增反馈")
    @PostMapping("/cieps/feedback")
    public Result addFeedbackofStu(@RequestHeader("authorization") String token,@RequestBody Feedback feedback){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        String userType = jwt.getClaim("userType").asString();
        feedback.setProviderType(userType);
        feedback.setProviderId(userId);
        feedbackService.insertFeedback(feedback);
        return Result.success();
    }
}
