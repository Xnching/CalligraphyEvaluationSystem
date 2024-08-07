package com.moyunzhijiao.system_backend.controller.dto.word;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class TemplateWordDTO {
    @ExcelIgnore
    Integer id;
    @ExcelProperty("字名")
    String name;
    @ExcelProperty("作者")
    String author;
    @ExcelProperty("文件名")
    String fileName;
    @ExcelProperty("结构")
    String structure;
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
    @ExcelIgnore
    String importer;
    @ExcelProperty("年级")
    String grade;
    @ExcelIgnore
    Integer gradeId;
}
