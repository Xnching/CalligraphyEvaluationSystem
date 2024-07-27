package com.moyunzhijiao.system_backend.controller;

import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.entiy.Radical;
import com.moyunzhijiao.system_backend.service.RadicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/backend/eccentricity")
public class RadicalController {

    @Autowired
    RadicalService radicalService;
    @GetMapping("/eccentricities")
    public Result findAllEccentricities(){
        List<Radical> list = radicalService.list();
        return Result.success(list);
    }
}
