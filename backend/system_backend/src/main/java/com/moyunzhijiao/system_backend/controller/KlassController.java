package com.moyunzhijiao.system_backend.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.controller.dto.KlassDTO;
import com.moyunzhijiao.system_backend.entiy.Klass;
import com.moyunzhijiao.system_backend.service.KlassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/backend/klass")
public class KlassController {
    @Autowired
    KlassService klassService;


    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "")String str,
                           @RequestParam Integer schoolId){
        IPage<KlassDTO> page = new Page<>(pageNum,pageSize);
        page = klassService.selectPage(page,schoolId,str);
        return Result.success(page);
    }


    @PutMapping("/delete")
    public Result deleteKlass(@RequestBody Map<String, String> params){
        String id = params.get("id");
        klassService.deleteKlass(id);
        return Result.success();
    }

    @PostMapping("/batch-add")
    public Result batchAddKlass(@RequestPart("id")  Integer id,
                                @RequestPart("file") MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        klassService.addKlassBatch(id,inputStream);
        return Result.success();
    }

    @PostMapping("/single-add")
    public Result singleAddKlass(@RequestBody KlassDTO klassDTO){
        klassService.addKlassSingle(klassDTO);
        return Result.success();
    }

    @GetMapping("/klasses")
    public Result findBySchoolAndGrade(@RequestParam Integer schoolId, @RequestParam Integer gradeId){
        List<Klass> list = klassService.getBySchoolAndGrade(schoolId,gradeId);
        return Result.success(list);
    }
}
