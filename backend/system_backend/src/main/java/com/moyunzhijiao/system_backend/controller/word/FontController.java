package com.moyunzhijiao.system_backend.controller.word;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.entiy.word.Font;
import com.moyunzhijiao.system_backend.service.word.FontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/backend/font")
public class FontController {
    @Autowired
    FontService fontService;
    @GetMapping("/fonts")
    public Result findAllFonts(){
        List<Font> list = fontService.list();
        return Result.success(list);
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "")String str){
        IPage<Font> page = new Page<>(pageNum,pageSize);
        page = fontService.selectPage(page,str);
        return Result.success(page);
    }

    @PostMapping("/add")
    public Result addFont(@RequestBody Map<String, String> params){
        String name = params.get("name");
        fontService.addFont(name);
        return Result.success();
    }

    @PutMapping("/delete")
    public Result deleteFont(@RequestBody Map<String, String> params){
        String id = params.get("id");
        fontService.deleteFont(id);
        return Result.success();
    }
}
