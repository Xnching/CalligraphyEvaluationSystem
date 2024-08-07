package com.moyunzhijiao.system_backend.controller.base;

import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.controller.dto.base.RegionDTO;
import com.moyunzhijiao.system_backend.service.base.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/backend/region")
public class RegionController {

    @Autowired
    RegionService regionService;

    @GetMapping("/page")
    public Result findPage(@RequestParam(defaultValue = "")String str){
        List<RegionDTO> p = regionService.findRegions(str);
        return Result.success(p);
    }

    /*
    * 获取所有省份id
    * */
    @GetMapping("/provinces")
    public Result findProvinces(){
        List<RegionDTO> provinces = regionService.getAllProvince();
        return Result.success(provinces);
    }

    /*
    * 根据省份id查找这个省份下面的所有市级地区
    * */
    @GetMapping("/cities")
    public Result findCities(@RequestParam Integer provinceId){
        List<RegionDTO> cities = regionService.getAllChildren(provinceId);
        return Result.success(cities);
    }

    /*
     * 根据市级id查找这个省份下面的所有县级地区
     * */
    @GetMapping("/counties")
    public Result findCounties(@RequestParam Integer cityId){
        List<RegionDTO> counties = regionService.getAllChildren(cityId);
        return Result.success(counties);
    }
}

