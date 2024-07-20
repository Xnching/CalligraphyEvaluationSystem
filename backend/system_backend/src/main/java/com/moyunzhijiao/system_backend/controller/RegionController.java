package com.moyunzhijiao.system_backend.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.controller.dto.RegionDTO;
import com.moyunzhijiao.system_backend.controller.dto.UserDTO;
import com.moyunzhijiao.system_backend.entiy.Region;
import com.moyunzhijiao.system_backend.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/backend/region")
public class RegionController {

    @Autowired
    RegionService regionService;

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "")String str){
        IPage<Region> page=new Page<>(pageNum,pageSize);
        IPage<RegionDTO> p = regionService.findRegions(page,str);
        System.out.println(p.getRecords());
        return Result.success(p);
    }

}

