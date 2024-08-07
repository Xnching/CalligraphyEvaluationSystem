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
import com.moyunzhijiao.system_backend.controller.dto.word.SampleWordDTO;
import com.moyunzhijiao.system_backend.entiy.word.SampleWord;
import com.moyunzhijiao.system_backend.exception.ServiceException;
import com.moyunzhijiao.system_backend.mapper.word.SampleWordMapper;
import com.moyunzhijiao.system_backend.service.base.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SampleWordService extends ServiceImpl<SampleWordMapper, SampleWord> {
    @Autowired
    SampleWordMapper sampleWordMapper;

    @Autowired
    GradeService gradeService;

    @Autowired
    RadicalService radicalService;

    @Autowired
    StructureService structureService;

    public List<String> listImporter() {
        QueryWrapper<SampleWord> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("distinct importer");
        return sampleWordMapper.selectObjs(queryWrapper).stream()
                .map(Object::toString)
                .collect(Collectors.toList());
    }

    public IPage<SampleWord> selectPage(IPage<SampleWord> page, String str, Integer structureId, Integer radicalId, Integer gradeId, String source, String importer) {
        QueryWrapper<SampleWord> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name",str);
        queryWrapper.like("source",source);
        queryWrapper.like("importer",importer);
        System.out.println("让我们看看查询条件是什么str："+str);
        System.out.println("让我们看看查询条件是什么structureId："+structureId);
        System.out.println("让我们看看查询条件是什么radicalId："+radicalId);
        System.out.println("让我们看看查询条件是什么gradeId："+gradeId);
        System.out.println("让我们看看查询条件是什么source："+source);
        System.out.println("让我们看看查询条件是什么importer："+importer);
        if(ObjectUtil.isNotNull(structureId)){
            queryWrapper.eq("structure_id",structureId);
        }
        if(ObjectUtil.isNotNull(gradeId)){
            queryWrapper.eq("grade_id",gradeId);
        }
        if(ObjectUtil.isNotNull(radicalId)){
            queryWrapper.eq("radical_id",radicalId);
        }
        // 使用Mapper的selectPage方法执行查询
        return sampleWordMapper.selectPage(page, queryWrapper);
    }

    public void deleteWord(String id) {
        int rows = sampleWordMapper.deleteById(id);
        if(rows!=1){
            throw new ServiceException(Constants.CODE_400,"删除参数错误！");
        }
    }

    public void addSampleWord(SampleWord sampleWord) {
        sampleWordMapper.insert(sampleWord);
    }

    public List<SampleWord> readExcelFile(MultipartFile excelFile) {
        // 读取Excel文件中的数据
        List<SampleWord> sampleWords = new ArrayList<>();
        try {
            EasyExcel.read(excelFile.getInputStream(), SampleWordDTO.class, new AnalysisEventListener<SampleWordDTO>() {
                @Override
                public void invoke(SampleWordDTO data, AnalysisContext context) {
                    //可以在此处检查
                    data.setGradeId(gradeService.getIdByField(data.getGrade()));
                    data.setRadicalId(radicalService.getIdByField(data.getRadical()));
                    data.setStructureId(structureService.getIdByField(data.getStructure()));
                    SampleWord tmp = convertToEntity(data);
                    sampleWords.add(tmp);
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
        return sampleWords;
    }

    public SampleWord findSampleWordByFileName(List<SampleWord> sampleWords, String fileName) {
        for (SampleWord sampleWord : sampleWords) {
            if (sampleWord.getFileName().equals(fileName)) {
                return sampleWord;
            }
        }
        return null;
    }
    public SampleWord convertToEntity(SampleWordDTO sampleWordDTO){
        SampleWord sampleWord = new SampleWord();
        BeanUtil.copyProperties(sampleWordDTO,sampleWord);
        return sampleWord;
    }
}
