package com.moyunzhijiao.system_backend.controller.word;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.entiy.word.Radical;
import com.moyunzhijiao.system_backend.entiy.word.Structure;
import com.moyunzhijiao.system_backend.service.word.StructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/backend/structure")
public class StructureController {
    @Autowired
    StructureService structureService;
    @GetMapping("/structures")
    public Result findAllStructures(){
        List<Structure> list = structureService.list();
        return Result.success(list);
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize){
        IPage<Structure> page = new Page<>(pageNum,pageSize);
        page = structureService.page(page);
        return Result.success(page);
    }
}
