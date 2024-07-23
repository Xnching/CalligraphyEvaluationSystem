package com.moyunzhijiao.system_backend.controller.dto;

import com.moyunzhijiao.system_backend.entiy.Grade;
import lombok.Data;

import java.util.List;

@Data
public class GradeDTO {
    Integer id;
    String name;
    private List<Grade> children;
}
