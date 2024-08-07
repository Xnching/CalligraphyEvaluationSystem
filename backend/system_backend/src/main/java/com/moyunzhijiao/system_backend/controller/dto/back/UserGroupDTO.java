package com.moyunzhijiao.system_backend.controller.dto.back;

import lombok.Data;

import java.util.List;

/*
 * UserGroupDTO用来发给前端用户组权限
 * */
@Data
public class UserGroupDTO {
    Integer id;
    String name;
    String state;
    Integer userCount;
    String description;
    private List<Integer> menus;
}
