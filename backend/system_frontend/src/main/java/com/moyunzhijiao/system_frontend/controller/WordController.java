package com.moyunzhijiao.system_frontend.controller;

import com.moyunzhijiao.system_frontend.common.Result;
import com.moyunzhijiao.system_frontend.entity.TemplateWord;
import com.moyunzhijiao.system_frontend.service.TemplateWordService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WordController {

    @Autowired
    TemplateWordService templateWordService;
    @Operation(summary = "根据部首来查找对应的模板字")
    @GetMapping("/ciep/radical")
    public Result findByRadical(@RequestParam Integer radicalId){
        List<TemplateWord> list = templateWordService.getByRadical(radicalId);
        return Result.success(list);
    }

    @Operation(summary = "根据结构来查找对应的模板字")
    @GetMapping("/ciep/structure")
    public Result findByStructure(@RequestParam Integer structureId){
        List<TemplateWord> list = templateWordService.getByStructure(structureId);
        return Result.success(list);
    }
}
