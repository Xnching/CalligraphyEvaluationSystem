package com.moyunzhijiao.system_app.service.word;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_app.common.Constants;
import com.moyunzhijiao.system_app.entity.word.TemplateWord;
import com.moyunzhijiao.system_app.exception.ServiceException;
import com.moyunzhijiao.system_app.mapper.word.TemplateWordMapper;
import com.moyunzhijiao.system_app.service.ConfigService;
import com.moyunzhijiao.system_app.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TemplateWordService extends ServiceImpl<TemplateWordMapper, TemplateWord> {
    @Autowired
    TemplateWordMapper templateWordMapper;

    /*
     * 批量返回模板字的本地路径
     * */
    public List<String> selectFilePathBatch(List<Integer> wordIds) {
        List<TemplateWord> templateWords = new ArrayList<>();
        for (Integer id : wordIds) {
            TemplateWord word = getById(id); // 假设 getById 是获取单个实例的方法
            if (word != null) {
                templateWords.add(word);
            }
        }
        return templateWords.stream().map(templateWord -> {
            String name = FileService.extractFileName(templateWord.getContent());
            return ConfigService.getTemplateWordFilePath()+name;
        }).toList();
    }

    /*
     * 根据一个字以及字体，找到对应的模板字
     * */
    public TemplateWord getPictureByWord(char word,Integer fontId){
        QueryWrapper<TemplateWord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",word);
        queryWrapper.eq("font_id",fontId);
        queryWrapper.select("id","content");
        List<TemplateWord> templateWords = templateWordMapper.selectList(queryWrapper);

        // 选择第一个记录
        TemplateWord templateWord = null;
        if (!templateWords.isEmpty()) {
            templateWord = templateWords.get(0);
        }
        if(templateWord == null)
            throw new ServiceException(Constants.CODE_500,"没有模板字："+word+" !");
        // 输出结果
        return templateWord;
    }
}
