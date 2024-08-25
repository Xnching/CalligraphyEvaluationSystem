package com.moyunzhijiao.system_backend.service.resource;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.controller.dto.resource.CalligraphyFactsDTO;

import com.moyunzhijiao.system_backend.entiy.resource.CalligraphyFacts;

import com.moyunzhijiao.system_backend.mapper.resource.CalligraphyFactsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalligraphyFactsService extends ServiceImpl<CalligraphyFactsMapper, CalligraphyFacts> {

    @Autowired
    CalligraphyFactsMapper calligraphyFactsMapper;

    public List<CalligraphyFactsDTO> findPage(){
        QueryWrapper<CalligraphyFacts> queryWrapper = new QueryWrapper<>();
        List<CalligraphyFacts> list = list(queryWrapper);
        List<CalligraphyFacts> parentNodes=list.stream()
                .filter(calligraphyFacts -> calligraphyFacts.getLevel() == 1).collect(Collectors.toList());
        for(CalligraphyFacts calligraphyFacts:parentNodes){
            calligraphyFacts.setChildren(getChildren(calligraphyFacts, list));
        }
        // 转换为 DTO
        List<CalligraphyFactsDTO> dtoList = parentNodes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return dtoList;
    }

    /*
     * 拼接子地区和父地区
     * */
    private List<CalligraphyFacts> getChildren(CalligraphyFacts parent, List<CalligraphyFacts> allCalligraphyFacts) {
        List<CalligraphyFacts> children = allCalligraphyFacts.stream().filter(calligraphyFacts -> calligraphyFacts.getParentId()!=null && calligraphyFacts.getParentId().equals(parent.getId())).collect(Collectors.toList());
        for (CalligraphyFacts child : children) {
            List<CalligraphyFacts> grandChildren = allCalligraphyFacts.stream().filter(calligraphyFacts -> calligraphyFacts.getParentId()!=null && calligraphyFacts.getParentId().equals(child.getId())).collect(Collectors.toList());
            child.setChildren(grandChildren);
        }
        return children;
    }

    /*
     * 将与数据库交互的转化为与前端交互的
     * */
    public CalligraphyFactsDTO convertToDTO(CalligraphyFacts calligraphyFacts) {
        CalligraphyFactsDTO dto = new CalligraphyFactsDTO();
        // 如果有不同的字段，需要自己进行转换
        BeanUtil.copyProperties(calligraphyFacts, dto);
        if (calligraphyFacts.getChildren() == null) {
            dto.setChildren(new ArrayList<>());
        }
        return dto;
    }

    public List<CalligraphyFacts> getSeconds(Integer id) {
        QueryWrapper<CalligraphyFacts> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",id);
        return list(queryWrapper);
    }

    public List<CalligraphyFacts> getAllSeconds() {
        QueryWrapper<CalligraphyFacts> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("level",2);
        return list(queryWrapper);
    }
}
