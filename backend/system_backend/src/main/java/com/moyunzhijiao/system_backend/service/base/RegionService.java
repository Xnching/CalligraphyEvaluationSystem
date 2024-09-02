package com.moyunzhijiao.system_backend.service.base;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.controller.dto.base.RegionDTO;
import com.moyunzhijiao.system_backend.controller.dto.base.SchoolDTO;
import com.moyunzhijiao.system_backend.entiy.base.Region;
import com.moyunzhijiao.system_backend.mapper.base.RegionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegionService extends ServiceImpl<RegionMapper, Region> {

    @Autowired
    RegionMapper regionMapper;

    /*
     * 用于查出地区，有模糊查询
     * */
    public List<Region> findRegions(String name){
        QueryWrapper<Region> queryWrapper=new QueryWrapper<>();
        if(StrUtil.isNotBlank(name)){
            //模糊查询
            queryWrapper.like("name",name);
        }
        List<Region> list = list(queryWrapper);
        List<Region> parentNodes=list.stream()
                .filter(region -> region.getLevel() == 1).toList();
        for(Region region:parentNodes){
            region.setChildren(getChildren(region, list));
        }

        return list;
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
        // 如果有不同的字段，需要自己进行转换
        BeanUtil.copyProperties(region, dto);
        if (region.getChildren() == null) {
            dto.setChildren(new ArrayList<>());
        }
        return dto;
    }

    /*
    * 给学校设置他的地区的三级id
    * */
    public SchoolDTO setThreeRegion(SchoolDTO schoolDTO){
        Region county = getRegion(schoolDTO.getRegionId());
        if (county != null) {
            schoolDTO.setRegionId(county.getId());
            Region city = getRegion(county.getParentId());
            if (city != null) {
                schoolDTO.setRegionId1(city.getId());
                Region province = getRegion(city.getParentId());
                if (province != null) {
                    schoolDTO.setRegionId2(province.getId());
                }
            }
        }
        return schoolDTO;
    }

    private Region getRegion(Integer id) {
        Region region = this.getById(id);
        if (region == null) {
            // 在这里处理 region 为 null 的情况，例如抛出异常或者返回一个默认值
        }
        return region;
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

    /*
    * 根据父id查找所有子地区，用于发给前端渲染下拉框
    *
    * 不用streamAPI的方法，根据数据库查询
    * Java Stream API：这种方法在内存中处理数据，如果你已经有了所有的区域数据，
    * 那么使用Stream API可以很方便地进行过滤操作。但是，如果区域数据非常大，
    * 那么将所有数据加载到内存中可能会消耗大量的内存资源。
    * MyBatis-Plus：这种方法直接在数据库层面进行过滤，只返回满足条件的数据，因此对内存的需求较小。
    * 但是，频繁的数据库查询可能会增加数据库的负载。
    * */
    public List<RegionDTO> getAllChildren(Integer fatherId){
        QueryWrapper<Region> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", fatherId);
        List<Region> regions = regionMapper.selectList(queryWrapper);
        return regions.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    /*
    * 获得所有省地区
    * */
    public List<RegionDTO> getAllProvince(){
        QueryWrapper<Region> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("level", 1);
        List<Region> regions = regionMapper.selectList(queryWrapper);
        return regions.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
}
