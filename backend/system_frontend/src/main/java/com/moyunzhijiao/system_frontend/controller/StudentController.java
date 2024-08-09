package com.moyunzhijiao.system_frontend.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyunzhijiao.system_frontend.common.Result;
import com.moyunzhijiao.system_frontend.controller.dto.StudentDTO;
import com.moyunzhijiao.system_frontend.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "搜索学生列表，带有年份年级班级个人信息四样模糊查询")
    @GetMapping("")
    public Result findPage(@RequestParam(defaultValue = "") String year,@RequestParam(defaultValue = "") String grade,
                           @RequestParam(defaultValue = "") String klass,@RequestParam(defaultValue = "") String stuInfo,
                           @RequestParam Integer currentPage, @RequestParam Integer pageSize){
        IPage<StudentDTO> page = new Page<>(currentPage,pageSize);
        return Result.success();
    }
}
