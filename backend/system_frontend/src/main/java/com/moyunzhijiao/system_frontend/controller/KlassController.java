package com.moyunzhijiao.system_frontend.controller;

import com.moyunzhijiao.system_frontend.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ciep")
public class KlassController {

    @GetMapping("/class-detail")
    public Result findStudentPageByKlassId(){
        return new Result();
    }
}
