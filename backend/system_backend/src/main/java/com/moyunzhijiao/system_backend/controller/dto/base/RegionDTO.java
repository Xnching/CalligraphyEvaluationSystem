package com.moyunzhijiao.system_backend.controller.dto.base;

import com.moyunzhijiao.system_backend.entiy.base.Region;
import lombok.Data;

import java.util.List;

@Data
public class RegionDTO {
    Integer id;
    String name;
    private List<Region> children;
}
