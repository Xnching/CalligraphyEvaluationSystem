package com.moyunzhijiao.system_backend.service.competition;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.entiy.competition.Competition;
import com.moyunzhijiao.system_backend.entiy.competition.Division;
import com.moyunzhijiao.system_backend.entiy.competition.DivisionRequirements;
import com.moyunzhijiao.system_backend.mapper.competition.DivisionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class DivisionService extends ServiceImpl<DivisionMapper, Division> {
    @Autowired
    DivisionMapper divisionMapper;
    @Autowired
    DivisionRequirementsService divisionRequirementsService;
    /*
    * 根据竞赛id获取组别信息
    * */
    public List<Division> getByCompetition(Integer id) {
        QueryWrapper<Division> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("competition_id",id);
        return list(queryWrapper);
    }

    /*
    * 更具竞赛逻辑删除组别
    * */
    @Transactional
    public void deleteByCompetition(Integer id) {
        QueryWrapper<Division> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("competition_id", id);
        List<Division> list = list(queryWrapper);
        if (!list.isEmpty()) {
            list.forEach(division -> division.setDeleteFlag(true));
            saveOrUpdateBatch(list);
        }
    }

    /*
    * 根据一个竞赛提供的多个组别来批量添加组别以及对应要求
    * */
    @Transactional
    public void saveBatchAndRequirement(List<Division> groups) {
        // 批量保存 Division
        saveBatch(groups);

        // 创建 DivisionRequirements 列表
        List<DivisionRequirements> divisionRequirementsList = groups.stream().map(division -> {
            DivisionRequirements divisionRequirements = new DivisionRequirements();
            divisionRequirements.setRequirements(division.getRequirement());
            divisionRequirements.setDivisionId(division.getId());
            return divisionRequirements;
        }).collect(Collectors.toList());

        // 批量保存 DivisionRequirements
        divisionRequirementsService.saveBatch(divisionRequirementsList);
    }

    public List<Division> getIngList() {
        List<Division> divisions = divisionMapper.selectIng();
        return divisions;
    }


    /*
    * 返回该竞赛下面的所有组别是否都处于已结束状态
    * */
    public boolean canCompetitionToEnd(Integer competitionId) {
        AtomicBoolean can = new AtomicBoolean(true);
        QueryWrapper<Division> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("competition_id",competitionId);
        List<Division> divisions = list(queryWrapper);
        divisions.forEach(division -> {
            //有一个不是已结束那么竞赛就返回false
            if(!division.getState().equals("已结束")){
                can.set(false);
            }
        });
        return can.get();
    }
}
