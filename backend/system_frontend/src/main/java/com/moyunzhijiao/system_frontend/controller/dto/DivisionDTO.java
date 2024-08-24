package com.moyunzhijiao.system_frontend.controller.dto;

import lombok.Data;

@Data
public class DivisionDTO {
    Integer id;
    String name;

    public DivisionDTO(Integer id, String name) {
        this.setId(id);
        this.setName(name);
    }
}
