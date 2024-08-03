package com.moyunzhijiao.system_frontend.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.entity.Region;
import com.moyunzhijiao.system_frontend.mapper.RegionMapper;
import org.springframework.stereotype.Service;

@Service
public class RegionService extends ServiceImpl<RegionMapper, Region> {
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
