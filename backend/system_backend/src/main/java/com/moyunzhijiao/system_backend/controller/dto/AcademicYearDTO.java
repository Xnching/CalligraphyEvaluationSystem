package com.moyunzhijiao.system_backend.controller.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

@Data
public class AcademicYearDTO {
    Integer id;
    String firstStart;
    String firstEnd;
    String secondStart;
    String secondEnd;
}
