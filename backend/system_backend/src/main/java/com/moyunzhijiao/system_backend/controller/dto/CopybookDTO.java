package com.moyunzhijiao.system_backend.controller.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class CopybookDTO {
    @ExcelIgnore
    Integer id;
    @ExcelProperty("字帖名")
    String name;
    @ExcelProperty("作者")
    String author;
    @ExcelProperty("字体")
    String font;
    @ExcelIgnore
    Integer fontId;
    @ExcelIgnore
    String content;     //图片的url
    @ExcelIgnore
    String importer;
    @ExcelProperty("文件名")
    String fileName;
    @ExcelProperty("年级")
    String grade;
    @ExcelIgnore
    Integer gradeId;
}
