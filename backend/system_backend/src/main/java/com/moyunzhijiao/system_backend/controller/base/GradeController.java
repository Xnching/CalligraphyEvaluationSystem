package com.moyunzhijiao.system_backend.controller.base;

import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.controller.dto.base.GradeDTO;
import com.moyunzhijiao.system_backend.service.base.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/backend/grade")
@PreAuthorize("hasAuthority('年级管理')")
public class GradeController {

    @Autowired
    GradeService gradeService;
    @GetMapping("/page")
    public Result findPage(){
        List<GradeDTO> p = gradeService.findRegions();
        return Result.success(p);
    }

    @GetMapping("/grades")
    public Result findAllGrades(){
        List<GradeDTO> list = gradeService.getAllGrades();
        return Result.success(list);
    }
}
