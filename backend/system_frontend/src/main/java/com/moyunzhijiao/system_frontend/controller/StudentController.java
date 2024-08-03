package com.moyunzhijiao.system_frontend.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.moyunzhijiao.system_frontend.common.Result;
import com.moyunzhijiao.system_frontend.service.StudentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "学生接口")
@RequestMapping("/api/frontend/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @PostMapping("/login")
    public Result login(){

        return Result.success();
    }

    @GetMapping("/information")
    public Result findInformation(@RequestHeader("token") String token){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        String studentId = jwt.getAudience().get(0);

        return Result.success();
    }
}
