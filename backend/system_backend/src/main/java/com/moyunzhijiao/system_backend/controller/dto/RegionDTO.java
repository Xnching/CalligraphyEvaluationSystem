package com.moyunzhijiao.system_backend.controller.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.moyunzhijiao.system_backend.entiy.Region;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RegionDTO {
    Integer id;
    String name;
    private List<Region> children;
}
