package com.moyunzhijiao.system_frontend.controller.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_frontend.entity.Klass;
import com.moyunzhijiao.system_frontend.entity.Student;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class KlassHomeworkDetailDTO {
    Integer id;
    String name;
    String type;
    String detailType;
    Integer fontId;
    String font;
    Integer wordCount;
    String source;      //来源
    BigDecimal score;
    String requirements;
    String target;
    String deadline;
    Integer difficulty;
    String createdTime;
    IPage<StudentDTO> studentList;
    IPage<Klass> classList;
}
