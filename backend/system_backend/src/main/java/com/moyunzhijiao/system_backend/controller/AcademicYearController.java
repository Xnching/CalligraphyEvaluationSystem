package com.moyunzhijiao.system_backend.controller;

import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.controller.dto.AcademicYearDTO;
import com.moyunzhijiao.system_backend.entiy.AcademicYear;
import com.moyunzhijiao.system_backend.service.AcademicYearService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/backend/academic-year")
public class AcademicYearController {
    @Autowired
    AcademicYearService academicYearService;
    @GetMapping("/page")
    public Result findTime(){
        AcademicYear academicYear = academicYearService.getById(1);
        return Result.success(academicYear);
    }

    @PostMapping("/update")
    public Result updateTime(@RequestBody AcademicYearDTO academicYearDTO){
        System.out.println(academicYearDTO.toString());
        AcademicYear academicYear = academicYearService.convertToEntity(academicYearDTO);
        academicYearService.updateById(academicYear);
        return Result.success();
    }
}
