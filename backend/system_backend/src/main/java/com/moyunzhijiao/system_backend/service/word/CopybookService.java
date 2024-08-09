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
import com.moyunzhijiao.system_backend.controller.dto.word.CopybookDTO;
import com.moyunzhijiao.system_backend.entiy.word.Copybook;
import com.moyunzhijiao.system_backend.exception.ServiceException;
import com.moyunzhijiao.system_backend.mapper.word.CopybookMapper;
import com.moyunzhijiao.system_backend.service.base.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CopybookService extends ServiceImpl<CopybookMapper, Copybook> {
    @Autowired
    CopybookMapper copybookMapper;
    @Autowired
    FontService fontService;
    @Autowired
    GradeService gradeService;

    public List<String> listAuthors() {
        QueryWrapper<Copybook> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("distinct author");
        return copybookMapper.selectObjs(queryWrapper).stream()
                .map(Object::toString)
                .collect(Collectors.toList());
    }

    public List<String> listImporter() {
        QueryWrapper<Copybook> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("distinct importer");
        return copybookMapper.selectObjs(queryWrapper).stream()
                .map(Object::toString)
                .collect(Collectors.toList());
    }

    public IPage<Copybook> selectPage(IPage<Copybook> page, String str, Integer fontId, Integer gradeId, String author, String importer) {
        QueryWrapper<Copybook> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name",str);
        queryWrapper.like("author",author);
        queryWrapper.like("importer",importer);
        if(ObjectUtil.isNotNull(gradeId)){
            queryWrapper.eq("grade_id",gradeId);
        }
        if(ObjectUtil.isNotNull(fontId)){
            queryWrapper.eq("font_id",fontId);
        }
        // 使用Mapper的selectPage方法执行查询
        return copybookMapper.selectPage(page, queryWrapper);

    }

    public void deleteCopybook(String id) {
        int rows = copybookMapper.deleteById(id);
        if(rows!=1){
            throw new ServiceException(Constants.CODE_400,"删除参数错误！");
        }
    }

    public void addCopybook(Copybook copybook) {
        copybookMapper.insert(copybook);
    }

    public List<Copybook> readExcelFile(MultipartFile excelFile) {
        // 读取Excel文件中的数据
        List<Copybook> copybooks = new ArrayList<>();
        try {
            EasyExcel.read(excelFile.getInputStream(), CopybookDTO.class, new AnalysisEventListener<CopybookDTO>() {
                @Override
                public void invoke(CopybookDTO data, AnalysisContext context) {
                    //可以在此处检查
                    data.setGradeId(gradeService.getIdByField(data.getGrade()));
                    data.setFontId(fontService.getIdByField(data.getFont()));
                    Copybook tmp = convertToEntity(data);
                    System.out.println("让我们看下原始文件名是多少："+tmp.getFileName());
                    copybooks.add(tmp);
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
        return copybooks;
    }

    private Copybook convertToEntity(CopybookDTO data) {
        Copybook copybook = new Copybook();
        BeanUtil.copyProperties(data,copybook);
        return copybook;
    }

    public Copybook findCopybookByFileName(List<Copybook> copybooks, String fileName) {
        for (Copybook copybook : copybooks){
            if(copybook.getFileName().equals(fileName)){
                return copybook;
            }
        }
        return null;
    }
}
