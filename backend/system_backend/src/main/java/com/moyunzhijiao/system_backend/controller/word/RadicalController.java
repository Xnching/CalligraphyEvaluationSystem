package com.moyunzhijiao.system_backend.controller.word;

import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.entiy.word.Radical;
import com.moyunzhijiao.system_backend.service.word.RadicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/backend/radical")
public class RadicalController {

    @Autowired
    RadicalService radicalService;
    @GetMapping("/radicals")
    public Result findAllRadicals(){
        List<Radical> list = radicalService.list();
        return Result.success(list);
    }
}
