package com.moyunzhijiao.system_backend.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.controller.dto.RegionDTO;
import com.moyunzhijiao.system_backend.controller.dto.SchoolDTO;
import com.moyunzhijiao.system_backend.controller.dto.UserDTO;
import com.moyunzhijiao.system_backend.entiy.Region;
import com.moyunzhijiao.system_backend.entiy.School;
import com.moyunzhijiao.system_backend.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/backend/school")
public class SchoolController {

    @Autowired
    SchoolService schoolService;
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "")String str){
        IPage<School> page=new Page<>(pageNum,pageSize);
        IPage<SchoolDTO> p = schoolService.selectSchools(page,str);
        System.out.println(p.getRecords());
        return Result.success(p);
    }

}
