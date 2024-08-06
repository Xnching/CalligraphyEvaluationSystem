package com.moyunzhijiao.system_frontend.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.moyunzhijiao.system_frontend.common.Constants;
import com.moyunzhijiao.system_frontend.common.Result;
import com.moyunzhijiao.system_frontend.controller.dto.QAFDTO;
import com.moyunzhijiao.system_frontend.entity.Feedback;
import com.moyunzhijiao.system_frontend.entity.Question;
import com.moyunzhijiao.system_frontend.service.FeedbackService;
import com.moyunzhijiao.system_frontend.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ciep")
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
    @GetMapping("/help-center")
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

    @Operation(summary = "教师获取1.常见问题2.建议与反馈")
    @PostMapping("/feedback")
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
}
