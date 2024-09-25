package com.moyunzhijiao.system_frontend.controller;

import com.moyunzhijiao.system_frontend.common.Result;
import com.moyunzhijiao.system_frontend.entity.TemplateWord;
import com.moyunzhijiao.system_frontend.entity.word.Font;
import com.moyunzhijiao.system_frontend.entity.word.Radical;
import com.moyunzhijiao.system_frontend.entity.word.Structure;
import com.moyunzhijiao.system_frontend.service.FontService;
import com.moyunzhijiao.system_frontend.service.TemplateWordService;
import com.moyunzhijiao.system_frontend.service.word.RadicalService;
import com.moyunzhijiao.system_frontend.service.word.StructureService;
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
    @Autowired
    RadicalService radicalService;
    @Autowired
    FontService fontService;
    @Autowired
    StructureService structureService;
    @Operation(summary = "根据部首来查找对应的模板字")
    @GetMapping("/ciep/radical-word")
    public Result findByRadical(@RequestParam Integer radicalId){
        List<TemplateWord> list = templateWordService.getByRadical(radicalId);
        return Result.success(list);
    }

    @Operation(summary = "根据笔画数来查找部首")
    @GetMapping("/ciep/radical")
    public Result findByCount(@RequestParam Integer count){
        List<Radical> list = radicalService.getByCount(count);
        return Result.success(list);
    }

    @Operation(summary = "根据结构来查找对应的模板字")
    @GetMapping("/ciep/structure-word")
    public Result findByStructure(@RequestParam Integer structureId){
        List<TemplateWord> list = templateWordService.getByStructure(structureId);
        return Result.success(list);
    }

    @Operation(summary = "获取字体")
    @GetMapping("/ciep/fonts")
    public Result findAllFonts(){
        List<Font> list = fontService.list();
        return Result.success(list);
    }

    @Operation(summary = "获取结构")
    @GetMapping("/ciep/structures")
    public Result findAllStructures(){
        List<Structure> list = structureService.list();
        return Result.success(list);
    }
}
