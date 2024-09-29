package com.moyunzhijiao.system_frontend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.entity.Grade;
import com.moyunzhijiao.system_frontend.mapper.GradeMapper;
import org.springframework.stereotype.Service;

@Service
public class GradeService extends ServiceImpl<GradeMapper, Grade> {
    public String getNameById(Integer id){
        Grade grade = getById(id);
        return grade.getName();
    }
}
