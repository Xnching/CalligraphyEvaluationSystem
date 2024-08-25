package com.moyunzhijiao.system_backend.service.outstanding;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.controller.dto.outstanding.OutstandingCompetitionDTO;
import com.moyunzhijiao.system_backend.entiy.outstanding.OutstandingCompetition;
import com.moyunzhijiao.system_backend.mapper.outstanding.OutstandingCompetitionMapper;
import com.moyunzhijiao.system_backend.service.competition.CSubmissionImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OutstandingCompetitionService extends ServiceImpl<OutstandingCompetitionMapper, OutstandingCompetition> {
    @Autowired
    OutstandingCompetitionMapper outstandingCompetitionMapper;
    @Autowired
    CSubmissionImageService cSubmissionImageService;

    public IPage<OutstandingCompetitionDTO> getPage(IPage<OutstandingCompetitionDTO> page, String str) {
        page = outstandingCompetitionMapper.page(page,str);
        long total = outstandingCompetitionMapper.selectCount(new QueryWrapper<>());
        page.setTotal(total);
        for (OutstandingCompetitionDTO dto:page.getRecords()){
            dto.setImageSrcList(cSubmissionImageService.getImages(dto.getSubmissionId()));
        }
        return page;
    }
}
