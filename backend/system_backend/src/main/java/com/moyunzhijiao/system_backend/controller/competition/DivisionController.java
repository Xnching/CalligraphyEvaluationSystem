package com.moyunzhijiao.system_backend.controller.competition;

import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.service.competition.DivisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/backend/division")
public class DivisionController {
    @Autowired
    DivisionService divisionService;
    @PutMapping("/delete")
    public Result deleteDivision(@RequestBody Map<String,String> r){
        String id = r.get("id");
        divisionService.removeById(id);
        return Result.success();
    }
}
