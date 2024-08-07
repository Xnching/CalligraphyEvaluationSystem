package com.moyunzhijiao.system_backend.controller.dto.word;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class SampleWordDTO {
    @ExcelIgnore
    Integer id;
    @ExcelProperty("字名")
    String name;
    @ExcelProperty("结构")
    String structure;
    @ExcelProperty("文件名")
    String fileName;
    @ExcelIgnore
    Integer structureId;
    @ExcelProperty("字体")
    String font;
    @ExcelIgnore
    Integer fontId;
    @ExcelProperty("部首")
    String radical;
    @ExcelIgnore
    Integer radicalId;
    @ExcelIgnore
    String content;     //图片的url
    String importer;
    @ExcelProperty("年级")
    String grade;
    @ExcelIgnore
    Integer gradeId;
    @ExcelProperty("来源")
    String source;

}
