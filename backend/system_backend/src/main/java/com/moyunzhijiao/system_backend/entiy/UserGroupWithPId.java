package com.moyunzhijiao.system_backend.entiy;

import lombok.Data;

@Data
public class UserGroupWithPId {
    private Integer id; // 用户组的 id
    private String name; // 用户组的名称
    private String state; // 用户组的状态
    private Integer userCount; // 用户组的用户数量
    private String description; // 用户组的描述
    private Integer permissionsId; // 权限 id
}
