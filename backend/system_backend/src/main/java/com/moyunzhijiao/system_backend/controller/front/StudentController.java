package com.moyunzhijiao.system_backend.controller.front;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.controller.dto.front.StudentDTO;
import com.moyunzhijiao.system_backend.service.base.SchoolService;
import com.moyunzhijiao.system_backend.service.front.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@RestController
@RequestMapping("/api/backend/student")
public class StudentController {
    @Autowired
    StudentService studentService;
    @Autowired
    SchoolService schoolService;

    @GetMapping("/students")
    public Result findAllByKlass(@RequestParam Integer pageNum,@RequestParam Integer pageSize,
                                 @RequestParam Integer klassId){
        IPage<StudentDTO> page = new Page<>(pageNum,pageSize);
        page = studentService.getStudentsByKlass(page,klassId);
        return Result.success(page);
    }


    @PutMapping("/klass-delete")
    public Result deleteKlass(@RequestBody Map<String, String> params){
        String id = params.get("id");
        studentService.deleteKlass(id);
        return Result.success();
    }


    @GetMapping("/page")
    public Result findStudents(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                               @RequestParam(defaultValue = "")String str,
                               @RequestParam Integer schoolId){
        IPage<StudentDTO> page = new Page<>(pageNum,pageSize);
        page = studentService.selectPage(page,schoolId,str);
        return Result.success(page);
    }


    @PutMapping("/update")
    public Result updateStudent(@RequestBody StudentDTO studentDTO){
        studentService.updateByDTO(studentDTO);
        return Result.success();
    }

    @PutMapping("/delete")
    public Result deleteStudent(@RequestBody Map<String, String> params){
        String id = params.get("id");
        studentService.deleteStudent(id);
        Integer schoolId = Integer.valueOf(params.get("schoolId"));
        schoolService.updateStudentCount(schoolId,-1);
        return Result.success();
    }

    @PostMapping("/single-add")
    public Result singleAddStudent(@RequestBody StudentDTO studentDTO){
        studentService.singleAdd(studentDTO);
        schoolService.updateStudentCount(studentDTO.getSchoolId(),1);
        return Result.success();
    }
    @PostMapping("/batch-add")
    public Result batchAddStudent(@RequestPart("id")  Integer id,
                                @RequestPart("file") MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        Integer count = studentService.addKlassBatch(id,inputStream);
        schoolService.updateStudentCount(id,count);
        return Result.success();
    }
}
