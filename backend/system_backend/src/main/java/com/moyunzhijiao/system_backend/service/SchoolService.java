package com.moyunzhijiao.system_backend.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.controller.dto.RegionDTO;
import com.moyunzhijiao.system_backend.controller.dto.SchoolDTO;
import com.moyunzhijiao.system_backend.entiy.Region;
import com.moyunzhijiao.system_backend.entiy.School;
import com.moyunzhijiao.system_backend.mapper.SchoolMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;




@Service
public class SchoolService extends ServiceImpl<SchoolMapper,School> {
    @Resource
    RegionService regionService;

    public IPage<SchoolDTO> selectSchools(IPage<School> page, String str) {
        QueryWrapper<School> queryWrapper=new QueryWrapper<>();
        if(StrUtil.isNotBlank(str)){
            //模糊查询
            queryWrapper.like("name",str);
            queryWrapper.like("address",str);
            queryWrapper.like("type",str);
            queryWrapper.like("leader",str);
        }
        IPage<School> pagedResult = page(page, queryWrapper);
        List<School> list = pagedResult.getRecords();
        // 转换为 DTO
        List<SchoolDTO> dtoList = list.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        //给每个学校赋值其所拥有的三级地区id
        dtoList = dtoList.stream()
                .map(this::setThreeRegion)
                .collect(Collectors.toList());
        // 创建新的 IPage 对象并设置数据
        IPage<SchoolDTO> dtoPage = new Page<>(page.getCurrent(), page.getSize(), pagedResult.getTotal());
        dtoPage.setRecords(dtoList);
        return dtoPage;
    }


    private SchoolDTO convertToDTO(School school) {
        SchoolDTO schoolDTO = new SchoolDTO();
        BeanUtil.copyProperties(school,schoolDTO);
        return schoolDTO;
    }

    public SchoolDTO setThreeRegion(SchoolDTO schoolDTO){
        Region region1 = getRegion(schoolDTO.getRegionId());
        if (region1 != null) {
            schoolDTO.setRegionId1(region1.getId());
            Region region2 = getRegion(region1.getParentId());
            if (region2 != null) {
                schoolDTO.setRegionId2(region2.getId());
            }
        }
        return schoolDTO;
    }

    private Region getRegion(Integer id) {
        Region region = regionService.getById(id);
        if (region == null) {
            // 在这里处理 region 为 null 的情况，例如抛出异常或者返回一个默认值
        }
        return region;
    }

}
