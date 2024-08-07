package com.moyunzhijiao.system_backend.controller.dto.front;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class KlassDTO {
    @ExcelIgnore
    Integer id;
    @ExcelProperty("班级名")
    String name;
    @ExcelProperty("年级")
    String grade;
    @ExcelIgnore
    Integer count;
    String teacher;
    String school;
    @ExcelProperty("入学年份")
    String year;
    @ExcelIgnore
    Integer schoolId;
    @ExcelIgnore
    Integer gradeId;
}
