package com.moyunzhijiao.system_backend.service.word;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.common.Constants;
import com.moyunzhijiao.system_backend.controller.dto.word.TemplateWordDTO;
import com.moyunzhijiao.system_backend.entiy.word.TemplateWord;
import com.moyunzhijiao.system_backend.exception.ServiceException;
import com.moyunzhijiao.system_backend.mapper.word.TemplateWordMapper;
import com.moyunzhijiao.system_backend.service.base.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TemplateWordService extends ServiceImpl<TemplateWordMapper, TemplateWord> {
    @Autowired
    TemplateWordMapper templateWordMapper;

    @Autowired
    GradeService gradeService;

    @Autowired
    RadicalService radicalService;

    @Autowired
    StructureService structureService;
    @Autowired
    FontService fontService;
    public List<String> listAuthors() {
        QueryWrapper<TemplateWord> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("distinct author");
        return templateWordMapper.selectObjs(queryWrapper).stream()
                .map(Object::toString)
                .collect(Collectors.toList());
    }

    public IPage<TemplateWord> selectPage(IPage<TemplateWord> page, String str, Integer structureId, Integer radicalId, Integer gradeId, Integer fontId, String author) {
        QueryWrapper<TemplateWord> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name",str);
        queryWrapper.like("author",author);
        if(ObjectUtil.isNotNull(structureId)){
            queryWrapper.eq("structure_id",structureId);
        }
        if(ObjectUtil.isNotNull(gradeId)){
            queryWrapper.eq("grade_id",gradeId);
        }
        if(ObjectUtil.isNotNull(radicalId)){
            queryWrapper.eq("radical_id",radicalId);
        }
        if(ObjectUtil.isNotNull(fontId)){
            queryWrapper.eq("font_id",fontId);
        }
        // 使用Mapper的selectPage方法执行查询
        return templateWordMapper.selectPage(page, queryWrapper);
    }

    public void deleteWord(String id) {
        int rows = templateWordMapper.deleteById(id);
        if(rows!=1){
            throw new ServiceException(Constants.CODE_400,"删除参数错误！");
        }
    }

    public void addTemplateWord(TemplateWord templateWord) {
        templateWordMapper.insert(templateWord);
    }

    @Transactional
    public List<TemplateWord> readExcelFile(MultipartFile excelFile) {
        // 读取Excel文件中的数据
        List<TemplateWord> templateWords = new ArrayList<>();
        try {
            EasyExcel.read(excelFile.getInputStream(), TemplateWordDTO.class, new AnalysisEventListener<TemplateWordDTO>() {
                @Override
                public void invoke(TemplateWordDTO data, AnalysisContext context) {
                    //可以在此处检查
                    data.setGradeId(gradeService.getIdByField(data.getGrade()));
                    data.setRadicalId(radicalService.getIdByField(data.getRadical()));
                    data.setStructureId(structureService.getIdByField(data.getStructure()));
                    data.setFontId(fontService.getIdByField(data.getFont()));
                    TemplateWord tmp = convertToEntity(data);
                    System.out.println("让我们看下原始文件名是多少："+tmp.getFileName());
                    templateWords.add(tmp);
                }
                @Override
                public void doAfterAllAnalysed(AnalysisContext context) {
                }
            }).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException(Constants.CODE_500,"系统错误");
            // 处理读取Excel文件的错误
        }
        return templateWords;
    }

    public TemplateWord findTemplateWordByFileName(List<TemplateWord> templateWords, String fileName) {
        for (TemplateWord templateWord : templateWords) {
            if (templateWord.getFileName().equals(fileName)) {
                return templateWord;
            }
        }
        return null;
    }
    public TemplateWord convertToEntity(TemplateWordDTO templateWordDTO){
        TemplateWord templateWord = new TemplateWord();
        BeanUtil.copyProperties(templateWordDTO,templateWord);
        return templateWord;
    }

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
        List<String> filePaths = templateWords.stream().map(TemplateWord::getFilePath).toList();
        return filePaths;
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
