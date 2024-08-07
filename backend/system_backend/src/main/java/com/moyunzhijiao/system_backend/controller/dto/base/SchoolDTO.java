package com.moyunzhijiao.system_backend.controller.dto.base;

import lombok.Data;

@Data
public class SchoolDTO {
    Integer id;
    String name;
    String address;
    String type;
    String leader;
    String leaderPhone;
    Integer regionId;
    Integer regionId1;
    Integer regionId2;
}
