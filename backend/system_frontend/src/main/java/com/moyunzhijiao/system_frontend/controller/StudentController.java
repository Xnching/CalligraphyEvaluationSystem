package com.moyunzhijiao.system_backend.controller;

import cn.hutool.core.util.StrUtil;
import com.moyunzhijiao.system_backend.common.Constants;
import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.controller.dto.StudentDTO;
import com.moyunzhijiao.system_backend.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/backend")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping("/login")
    public Result login(@RequestBody StudentDTO studentDTO){
        String login_id = studentDTO.getLoginId();
        String password = studentDTO.getPassword();
        //调用hutool工具中的StrUtil函数实现用户名和密码是否为空的判断
        if(StrUtil.isBlank(login_id) || StrUtil.isBlank(password)){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        StudentDTO dto = studentService.login(studentDTO);
        return Result.success(dto);
    }
}
