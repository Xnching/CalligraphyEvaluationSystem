package com.moyunzhijiao.system_backend.controller.resource;

import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.controller.dto.resource.CalligraphyFactsDTO;
import com.moyunzhijiao.system_backend.entiy.resource.CalligraphyFacts;
import com.moyunzhijiao.system_backend.service.resource.CalligraphyFactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/backend/calligraphy-facts")
public class CalligraphyFactsController {

    @Autowired
    CalligraphyFactsService calligraphyFactsService;

    @GetMapping("/page")
    public Result findPage(){
        List<CalligraphyFactsDTO> page = calligraphyFactsService.findPage();
        return Result.success(page);
    }

    @GetMapping("/seconds")
    public Result findSeconds(@RequestParam Integer id) {
        List<CalligraphyFacts> list = calligraphyFactsService.getSeconds(id);
        return Result.success(list);
    }


    @GetMapping("/all-seconds")
    public Result findAllSeconds(){
        List<CalligraphyFacts>list = calligraphyFactsService.getAllSeconds();
        return Result.success(list);
    }
}
