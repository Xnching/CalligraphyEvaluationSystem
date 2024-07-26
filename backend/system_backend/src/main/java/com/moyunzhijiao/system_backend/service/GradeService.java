package com.moyunzhijiao.system_backend.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.controller.dto.GradeDTO;
import com.moyunzhijiao.system_backend.mapper.GradeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.moyunzhijiao.system_backend.entiy.Grade;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GradeService extends ServiceImpl<GradeMapper,Grade> {
    @Autowired
    GradeMapper gradeMapper;
    public List<GradeDTO> findRegions() {
        QueryWrapper<Grade> queryWrapper = new QueryWrapper<>();
        List<Grade> list = list(queryWrapper);
        List<Grade> parentNodes = list.stream().filter(grade -> grade.getLevel()==1).collect(Collectors.toList());
        for(Grade grade:parentNodes){
            grade.setChildren(getChildren(grade,list));
        }
        List<GradeDTO> dtoList = parentNodes.stream().map(this::convertToDTO).collect(Collectors.toList());
        return dtoList;
    }

    private List<Grade> getChildren(Grade parent,List<Grade> allGrade){
        List<Grade> children = allGrade.stream().filter(
                grade->grade.getParentId()!=null &&
                        grade.getParentId().equals(parent.getId()))
                .collect(Collectors.toList());
        return children;
    }

    public List<GradeDTO> getAllGrades(){
        QueryWrapper<Grade> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("level",2);
        List<Grade> list = list(queryWrapper);
        List<GradeDTO> dtoList = list.stream().map(this::convertToDTO).collect(Collectors.toList());
        return dtoList;
    }

    private GradeDTO convertToDTO(Grade grade){
        GradeDTO gradeDTO = new GradeDTO();
        BeanUtil.copyProperties(grade,gradeDTO);
        return gradeDTO;
    }

    public Integer getIdByField(String name){
        QueryWrapper<Grade> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id").eq("name", name);
        Grade grade = gradeMapper.selectOne(queryWrapper);
        return grade != null ? grade.getId() : null;
    }


}
