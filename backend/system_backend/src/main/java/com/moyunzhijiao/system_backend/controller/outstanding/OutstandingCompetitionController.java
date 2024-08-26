package com.moyunzhijiao.system_backend.controller.outstanding;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.controller.dto.outstanding.OutstandingCompetitionDTO;
import com.moyunzhijiao.system_backend.service.outstanding.OutstandingCompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/backend/outstanding-competition")
public class OutstandingCompetitionController {

    @Autowired
    OutstandingCompetitionService outstandingCompetitionService;
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "")String str){
        IPage<OutstandingCompetitionDTO> page = new Page<>(pageNum,pageSize);
        page = outstandingCompetitionService.getPage(page,str);
        return Result.success(page);
    }

    @DeleteMapping("/delete")
    public Result deleteOutstanding(@RequestBody Map<String,String> r){
        Integer submissionId = Integer.valueOf(r.get("id"));
        outstandingCompetitionService.removeById(submissionId);
        return Result.success();
    }
}
