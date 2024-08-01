package com.moyunzhijiao.system_backend.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class CalligraphyFactsDTO {
    Integer id;
    String name;
    private List<CalligraphyFactsDTO> children;
}
