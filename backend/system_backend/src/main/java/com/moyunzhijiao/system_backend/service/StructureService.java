package com.moyunzhijiao.system_backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.entiy.Structure;
import com.moyunzhijiao.system_backend.mapper.StructureMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StructureService extends ServiceImpl<StructureMapper, Structure> {
    @Autowired
    StructureMapper structureMapper;
    public Integer getIdByField(String name){
        QueryWrapper<Structure> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id").eq("name",name);
        Structure structure = structureMapper.selectOne(queryWrapper);
        return structure != null ? structure.getId():null;
    }
}
