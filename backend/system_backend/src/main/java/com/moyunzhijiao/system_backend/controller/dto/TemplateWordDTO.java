package com.moyunzhijiao.system_backend.controller.dto;

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
    Integer structureId;
    @ExcelProperty("字体")
    String font;
    Integer fontId;
    @ExcelProperty("部首")
    String radical;
    Integer radicalId;
    String content;     //图片的url
    String importer;
    @ExcelProperty("年级")
    String grade;
    Integer gradeId;
}
