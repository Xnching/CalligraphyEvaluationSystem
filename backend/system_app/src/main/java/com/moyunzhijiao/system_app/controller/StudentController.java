package com.moyunzhijiao.system_app.controller;

import cn.hutool.core.util.StrUtil;
import com.moyunzhijiao.system_app.common.Constants;
import com.moyunzhijiao.system_app.common.Result;
import com.moyunzhijiao.system_app.controller.dto.StudentDTO;
import com.moyunzhijiao.system_app.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "学生接口")
@RequestMapping("")
public class StudentController {
    @Autowired
    StudentService studentService;
    @Operation(summary = "学生登陆，登录注册之类的需要到配置文件中添加放行路径，在InterceptorConfig文件中，否则会被token拦截验证")
    @Parameters({
            @Parameter(name = "userName",description = "账户登录名",in = ParameterIn.PATH),
            @Parameter(name = "userPassword",description = "密码",in = ParameterIn.PATH)
    })
    @GetMapping("/LoginService/login")
    public Result login(@RequestParam("userName") String name,@RequestParam String userPassword){
        //调用hutool工具中的StrUtil函数实现用户名和密码是否为空的判断
        if(StrUtil.isBlank(name) || StrUtil.isBlank(userPassword)){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        StudentDTO studentDTO = studentService.login(name,userPassword);
        return Result.success(studentDTO);
    }


}
