package com.moyunzhijiao.system_backend.controller.dto.base;

import com.moyunzhijiao.system_backend.entiy.base.Grade;
import lombok.Data;

import java.util.List;

@Data
public class GradeDTO {
    Integer id;
    String name;
    private List<Grade> children;
}
