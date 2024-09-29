package com.moyunzhijiao.system_backend.service.back;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_backend.controller.dto.back.UserDTO;
import com.moyunzhijiao.system_backend.entiy.back.Permissions;
import com.moyunzhijiao.system_backend.entiy.back.User;

import java.util.List;

public interface UserService {
    UserDTO login(UserDTO userDTO);
    List<Permissions> getMenus(Integer userGroupId);
    IPage<UserDTO> selectPageWithGroupName(IPage<UserDTO> page, String str);
    void updateUser(UserDTO userDTO);
    void deleteUser(String id);
    void addUser(UserDTO userDTO);
    String getNameById(String id);
}
