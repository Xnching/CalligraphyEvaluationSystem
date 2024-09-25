package com.moyunzhijiao.system_backend.controller.word;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.entiy.word.Radical;
import com.moyunzhijiao.system_backend.service.word.RadicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/backend/radical")
@PreAuthorize("hasAuthority('评阅管理')")
public class RadicalController {

    @Autowired
    RadicalService radicalService;
    @GetMapping("/radicals")
    public Result findAllRadicals(){
        List<Radical> list = radicalService.list();
        return Result.success(list);
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize){
        IPage<Radical> page = new Page<>(pageNum,pageSize);
        page = radicalService.page(page);
        return Result.success(page);
    }
}
