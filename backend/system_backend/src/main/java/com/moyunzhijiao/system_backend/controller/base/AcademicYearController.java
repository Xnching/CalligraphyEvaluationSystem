package com.moyunzhijiao.system_backend.controller.base;

import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.controller.dto.base.AcademicYearDTO;
import com.moyunzhijiao.system_backend.entiy.base.AcademicYear;
import com.moyunzhijiao.system_backend.service.base.AcademicYearService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/backend/academic-year")
@PreAuthorize("hasAuthority('学年管理')")
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
