package com.moyunzhijiao.system_backend.controller;

import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.controller.dto.GradeDTO;
import com.moyunzhijiao.system_backend.controller.dto.RegionDTO;
import com.moyunzhijiao.system_backend.entiy.Grade;
import com.moyunzhijiao.system_backend.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/backend/grade")
public class GradeController {

    @Autowired
    GradeService gradeService;
    @GetMapping("/page")
    public Result findPage(){
        List<GradeDTO> p = gradeService.findRegions();
        return Result.success(p);
    }
}
