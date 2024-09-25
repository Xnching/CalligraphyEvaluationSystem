package com.moyunzhijiao.system_backend.controller.front;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyunzhijiao.system_backend.common.Constants;
import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.controller.dto.back.UserDTO;
import com.moyunzhijiao.system_backend.entiy.front.Teacher;
import com.moyunzhijiao.system_backend.service.base.SchoolService;
import com.moyunzhijiao.system_backend.service.front.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@RestController
@RequestMapping("/api/backend/teacher")
public class TeacherController {
    @Autowired
    TeacherService teacherService;
    @Autowired
    SchoolService schoolService;

    @PreAuthorize("hasAuthority('教师管理')")
    @GetMapping("/page")
    public Result findTeachers(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                               @RequestParam(defaultValue = "")String str,
                               @RequestParam Integer schoolId){
        IPage<Teacher>page = new Page<>(pageNum,pageSize);
        page = teacherService.selectPage(page,schoolId,str);
        return Result.success(page);
    }

    @PreAuthorize("hasAuthority('教师管理')")
    @PutMapping("/update")
    public Result updateTeacher(@RequestBody Teacher teacher){
        if(StrUtil.isEmpty(teacher.getPassword())){
            teacher.setPassword(teacher.getWorkno());
        }
        teacherService.updateById(teacher);
        return Result.success();
    }

    @PreAuthorize("hasAuthority('教师管理')")
    @PutMapping("/delete")
    public Result deleteTeacher(@RequestBody Map<String, String> params){
        String id = params.get("id");
        teacherService.deleteTeacher(id);
        Integer schoolId = Integer.valueOf(params.get("schoolId"));
        schoolService.updateTeacherCount(schoolId,-1);
        return  Result.success();
    }

    @PreAuthorize("hasAuthority('教师管理')")
    @PostMapping("/single-add")
    public Result singleAddTeacher(@RequestBody Teacher teacher){
        teacherService.singleAdd(teacher);
        schoolService.updateTeacherCount(teacher.getSchoolId(),1);
        return Result.success();
    }

    @PreAuthorize("hasAuthority('教师管理')")
    @PostMapping("/batch-add")
    public Result batchAddTeacher(@RequestPart("id")  Integer id,
                                  @RequestPart("file") MultipartFile file) throws IOException{
        InputStream inputStream = file.getInputStream();
        Integer count = teacherService.addTeacherBatch(id,inputStream);
        schoolService.updateTeacherCount(id,count);
        return Result.success();
    }
    @GetMapping("/name")
    public Result getName(@RequestParam Integer teacherId){
        Teacher teacher = teacherService.getById(teacherId);
        return Result.success(teacher.getName());
    }

    /*
     * 评阅教师的登录接口
     * */
    @PostMapping("/login")
    public Result login(@RequestBody Teacher teacher){
        String phone = teacher.getLoginId();
        teacher.setPhone(phone);
        String password = teacher.getPassword();

        //调用hutool工具中的StrUtil函数实现用户名和密码是否为空的判断
        if(StrUtil.isBlank(phone) || StrUtil.isBlank(password)){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        Teacher dto = teacherService.login(teacher);
        return Result.success(dto);
    }
}
