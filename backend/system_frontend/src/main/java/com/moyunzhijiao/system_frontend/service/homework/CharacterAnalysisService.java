package com.moyunzhijiao.system_frontend.service.homework;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.entity.homework.CharacterAnalysis;
import com.moyunzhijiao.system_frontend.mapper.homework.CharacterAnalysisMapper;
import com.moyunzhijiao.system_frontend.service.TemplateWordService;
import com.moyunzhijiao.system_frontend.service.resource.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterAnalysisService extends ServiceImpl<CharacterAnalysisMapper, CharacterAnalysis> {
    @Autowired
    CharacterAnalysisMapper characterAnalysisMapper;
    @Autowired
    StrokeAnalysisService strokeAnalysisService;
    @Autowired
    VideoService videoService;
    @Autowired
    TemplateWordService templateWordService;

    /*
    * 获取单字分析列表，其中携带写这个字的url以及这个字的笔画分析
    * */
    public List<CharacterAnalysis> getBySubmission(Integer submissionId){
        QueryWrapper<CharacterAnalysis> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("submission_id",submissionId);
        List<CharacterAnalysis> list = list(queryWrapper);
        for (CharacterAnalysis characterAnalysis:list){
            Integer characterId = characterAnalysis.getId();
            String wordName = characterAnalysis.getName();
            String templateUrl = templateWordService.getByHomework(wordName);
            characterAnalysis.setTemplateWordUrl(templateUrl);
            characterAnalysis.setStrokeList(strokeAnalysisService.getByCharacter(characterId));
            characterAnalysis.setVideoUrl(videoService.getVideoUrlByWriteWord(wordName));
        }
        return list;
    }
}
