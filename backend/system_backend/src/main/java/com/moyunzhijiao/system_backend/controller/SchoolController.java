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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    /*
     * 编辑弹窗中的更新用户数据
     * */
    @PutMapping("/update")
    public Result updateUser(@RequestBody SchoolDTO schoolDTO){
        schoolService.updateSchool(schoolDTO);
        return Result.success();
    }

    /*
     * 删除一行用户数据
     * */
    @PutMapping("/delete")
    public Result deleteUser(@RequestBody Map<String, String> params){
        String id = params.get("id");
        schoolService.deleteSchool(id);
        return Result.success();
    }

    /*
     * 新增一个用户
     * */
    @PostMapping("/add")
    public Result addUser(@RequestBody SchoolDTO schoolDTO){
        schoolService.addSchool(schoolDTO);
        return Result.success();
    }

    /*
    * 用来渲染学校下拉框
    * */
    @GetMapping("/schools")
    public Result findSchools(@RequestParam Integer countyId,@RequestParam String type){
        List<School> schools = schoolService.findSchoolsByRegion(countyId,type);
        return Result.success(schools);
    }

}
