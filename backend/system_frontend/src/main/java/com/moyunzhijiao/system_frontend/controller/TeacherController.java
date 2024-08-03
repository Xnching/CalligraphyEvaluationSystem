package com.moyunzhijiao.system_frontend.controller;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.moyunzhijiao.system_frontend.common.Constants;
import com.moyunzhijiao.system_frontend.common.Result;
import com.moyunzhijiao.system_frontend.controller.dto.QAFDTO;
import com.moyunzhijiao.system_frontend.controller.dto.TeacherDTO;
import com.moyunzhijiao.system_frontend.entity.Teacher;
import com.moyunzhijiao.system_frontend.service.FeedbackService;
import com.moyunzhijiao.system_frontend.service.QuestionService;
import com.moyunzhijiao.system_frontend.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Tag(name = "教师接口")
@RequestMapping("/ciep")
public class TeacherController {
    @Autowired
    TeacherService teacherService;
    @Autowired
    QuestionService questionService;
    @Autowired
    FeedbackService feedbackService;

    @Operation(summary = "登录，返回的类里有token字段")
    @PostMapping("/login")
    public Result login(@RequestBody TeacherDTO teacherDTO){
        String workno = teacherDTO.getWorkno();
        String password = teacherDTO.getPassword();
        //调用hutool工具中的StrUtil函数实现用户名和密码是否为空的判断
        if(StrUtil.isBlank(workno) || StrUtil.isBlank(password)){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        TeacherDTO dto = teacherService.login(teacherDTO);
        return Result.success(dto);
    }

    @Operation(summary = "教师获取个人信息")
    @Parameters({
            @Parameter(name = "type",description = "请求类型",in = ParameterIn.PATH)
    })
    @GetMapping("/my-info")
    public Result findInformation(@RequestHeader("token") String token){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer teacherId = Integer.valueOf(jwt.getAudience().get(0));
        TeacherDTO teacherDTO = teacherService.getInformation(teacherId);
        return Result.success(teacherDTO);
    }

    @Operation(summary = "教师获取1.常见问题2.建议与反馈")
    @Parameters({
            @Parameter(name = "type",description = "请求类型",in = ParameterIn.PATH),
            @Parameter(name = "token",description = "请求token",required = true,in = ParameterIn.HEADER),
    })
    @GetMapping("/help-center")
    public Result findQAF(@RequestHeader("token") String token,@RequestParam String type){
        int t = Integer.valueOf(type);
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer teacherId = Integer.valueOf(jwt.getAudience().get(0));
        QAFDTO qafdto;
        switch (t){
            case 1:
                qafdto = questionService.getQuestions();
                break;
            case 2:
                qafdto = feedbackService.getFeedback("教师",teacherId);
                break;
            default:return Result.error(Constants.CODE_400,"错误的type类型！");
        }
        return Result.success(qafdto);
    }
    @Operation(summary = "更新用户信息")
    @PutMapping("/my-info")
    public Result updateTeacher(@RequestBody TeacherDTO teacherDTO){
        teacherService.updateInformation(teacherDTO);
        return Result.success();
    }

    @Operation(summary = "修改头像")
    @Parameters({
            @Parameter(name = "token",description = "请求token",required = true,in = ParameterIn.HEADER),
            @Parameter(name = "avator",description = "文件名称",required = true,in = ParameterIn.DEFAULT, content = @Content(mediaType = "multipart/form-data"))
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功"),
            @ApiResponse(responseCode = "400", description = "请求错误"),
            @ApiResponse(responseCode = "500", description = "服务器错误")
    })
    @PostMapping("/avator")
    public Result updateAvator(@RequestHeader("token") String token,@RequestPart("avator")MultipartFile avator){
        if(avator.isEmpty()){
            return Result.error(Constants.CODE_400,"文件为空！");
        }
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer teacherId = Integer.valueOf(jwt.getAudience().get(0));
        teacherService.updateAvator(teacherId,avator);
        return Result.success();
    }

    /*
    * 用来展示测试Knife4j的样子
    * */
    @Operation(summary = "用来展示测试Knife4j的样子")
    @Parameters({
            @Parameter(name = "id",description = "文件id",in = ParameterIn.PATH),
            @Parameter(name = "token",description = "请求token",required = true,in = ParameterIn.HEADER),
            @Parameter(name = "name",description = "文件名称",required = true,in=ParameterIn.QUERY)
    })
    @PostMapping("/bodyParamHeaderPath/{id}")
    public Result bodyParamHeaderPath(@PathVariable("id") String id,@RequestHeader("token") String token, @RequestParam("name")String name,@RequestBody Teacher fileResp){

        return Result.success();
    }
}
