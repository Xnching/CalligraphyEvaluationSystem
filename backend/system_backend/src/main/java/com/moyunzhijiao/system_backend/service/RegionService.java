package com.moyunzhijiao.system_backend.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.controller.dto.RegionDTO;
import com.moyunzhijiao.system_backend.entiy.Region;
import com.moyunzhijiao.system_backend.mapper.RegionMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegionService extends ServiceImpl<RegionMapper, Region> {

    /*
     * 用于查出地区，有模糊查询
     * */
    public IPage<RegionDTO> findRegions(IPage<Region> page, String name){
        QueryWrapper<Region> queryWrapper=new QueryWrapper<>();
        if(StrUtil.isNotBlank(name)){
            //模糊查询
            queryWrapper.like("name",name);
        }
        IPage<Region> pagedResult = page(page, queryWrapper);
        List<Region> list = pagedResult.getRecords();
        List<Region> parentNodes=list.stream()
                .filter(region -> region.getLevel() == 1).collect(Collectors.toList());
        for(Region region:parentNodes){
            region.setChildren(getChildren(region, list));
        }
        // 转换为 DTO
        List<RegionDTO> dtoList = parentNodes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        // 创建新的 IPage 对象并设置数据
        IPage<RegionDTO> dtoPage = new Page<>(page.getCurrent(), page.getSize(), pagedResult.getTotal());
        dtoPage.setRecords(dtoList);
        return dtoPage;
    }

    /*
     * 拼接子地区和父地区
     * */
    private List<Region> getChildren(Region parent, List<Region> allRegion) {
        List<Region> children = allRegion.stream().filter(region -> region.getParentId()!=null && region.getParentId().equals(parent.getId())).collect(Collectors.toList());
        for (Region child : children) {
            List<Region> grandChildren = allRegion.stream().filter(region -> region.getParentId()!=null && region.getParentId().equals(child.getId())).collect(Collectors.toList());
            child.setChildren(grandChildren);
        }
        return children;
    }

    /*
    * 将与数据库交互的转化为与前端交互的
    * */
    public RegionDTO convertToDTO(Region region) {
        RegionDTO dto = new RegionDTO();
        // 如果有不同的字段，您需要自己进行转换
        BeanUtils.copyProperties(region, dto);
        if (region.getChildren() == null) {
            dto.setChildren(new ArrayList<>());
        }
        return dto;
    }

    /*
    * 根据id获得一个地区的完整名称
    * */
    public String selectAllNameById(Integer id){
        StringBuilder allName = new StringBuilder();
        Region region = getById(id);
        while (region != null) {
            allName.insert(0, region.getName());
            region = getById(region.getParentId());
        }
        return allName.toString();
    }

}
