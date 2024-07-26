package com.moyunzhijiao.system_backend.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.controller.dto.StudentDTO;
import com.moyunzhijiao.system_backend.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/backend/student")
public class StudentController {
    @Autowired
    StudentService studentService;

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
        return Result.success();
    }

    @PostMapping("/single-add")
    public Result singleAddStudent(@RequestBody StudentDTO studentDTO){
        studentService.singleAdd(studentDTO);
        return Result.success();
    }
    @PostMapping("/batch-add")
    public Result batchAddStudent(@RequestPart("id")  Integer id,
                                @RequestPart("file") MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        studentService.addKlassBatch(id,inputStream);
        return Result.success();
    }
}
