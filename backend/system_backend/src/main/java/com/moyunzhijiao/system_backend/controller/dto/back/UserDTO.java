package com.moyunzhijiao.system_backend.controller.dto.back;

import com.moyunzhijiao.system_backend.entiy.back.Permissions;
import lombok.Data;
import java.util.List;
/*
 * UserDTO用来接受前端登录时传递的用户名和密码,或用来发给前端
 * */
@Data
public class UserDTO {
    private Integer id;
    private String name;
    private String password;
    private String loginId;
    private String token;
    private String phone;
    private String userGroupName;
    private String userGroupId;
    private List<Permissions> menus;
}
