package com.moyunzhijiao.system_frontend.controller;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyunzhijiao.system_frontend.common.Constants;
import com.moyunzhijiao.system_frontend.common.Result;
import com.moyunzhijiao.system_frontend.controller.dto.StudentDTO;
import com.moyunzhijiao.system_frontend.entity.Student;
import com.moyunzhijiao.system_frontend.service.KlassService;
import com.moyunzhijiao.system_frontend.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@Tag(name = "学生接口")
public class StudentController {
    @Autowired
    StudentService studentService;
    @Autowired
    KlassService klassService;

    @PostMapping("/cieps/login")
    public Result login(@RequestBody StudentDTO studentDTO){
        String student_number=studentDTO.getStuno();
        String password= studentDTO.getPassword();
        if(StrUtil.isBlank(student_number) || StrUtil.isBlank(password)){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        StudentDTO dto=studentService.login(studentDTO);
        return Result.success(dto);
    }

    @Operation(summary = "获取学生列表，根据班级id查一个班级的学生列表，其中名字模糊查询")
    @GetMapping("/ciep/student")
    public Result findPage(@RequestParam(defaultValue = "") String name, @RequestParam Integer classId){
        List<StudentDTO> list = studentService.getByKlass(classId,name);
        return Result.success(list);
    }

    @Operation(summary = "获取一个教师的所有学生列表，其中名字模糊查询")
    @GetMapping("/ciep/students")
    public Result findAllStudent(@RequestHeader("authorization") String token,@RequestParam(defaultValue = "") String name){
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer teacherId = Integer.valueOf(jwt.getAudience().get(0));
        List<StudentDTO> allList = studentService.getAllByTeacher(teacherId,name);
        return Result.success(allList);
    }

    @PostMapping("/cieps/update-avatar")
    public Result updateAvatar(@RequestHeader("authorization") String token,@RequestPart("avatar") MultipartFile avatar){
        if(avatar.isEmpty()){
            return Result.error(Constants.CODE_400,"文件为空！");
        }
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer stuId = Integer.valueOf(jwt.getAudience().get(0));
        String url = studentService.updateAvatar(stuId,avatar);
        return Result.success(url);
    }

}
