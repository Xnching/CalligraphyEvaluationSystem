package com.moyunzhijiao.system_frontend.service.homework;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.entity.homework.StrokeAnalysis;
import com.moyunzhijiao.system_frontend.mapper.homework.StrokeAnalysisMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StrokeAnalysisService extends ServiceImpl<StrokeAnalysisMapper, StrokeAnalysis> {
    @Autowired
    StrokeAnalysisMapper strokeAnalysisMapper;
    /*
    * 根据单字分析获得笔画分析
    * */
    public List<StrokeAnalysis> getByCharacter(Integer characterId){
        QueryWrapper<StrokeAnalysis> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("character_analysis_id",characterId);
        return list(queryWrapper);
    }
}
