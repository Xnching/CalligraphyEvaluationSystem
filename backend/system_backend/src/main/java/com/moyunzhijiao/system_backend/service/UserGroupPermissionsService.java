package com.moyunzhijiao.system_backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.controller.dto.PermissionsDTO;
import com.moyunzhijiao.system_backend.entiy.UserGroupPermissions;
import com.moyunzhijiao.system_backend.mapper.UserGroupPermissionsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserGroupPermissionsService extends ServiceImpl<UserGroupPermissionsMapper,UserGroupPermissions> {

    @Autowired
    UserGroupPermissionsMapper userGroupPermissionsMapper;

    /*
    * 根据用户组id查id
    * */
    public List<Integer> getIdByUserGroupId(Integer userGroupId){
        return userGroupPermissionsMapper.selectIdById(userGroupId);
    }

    /*
    * 根据用户组id查permissionsDTO
    * */
    public List<PermissionsDTO> getDTOByUserGroupId(Integer userGroupId){
        return userGroupPermissionsMapper.selectIdAndNameById(userGroupId);
    }


    /*
    * 更新用户组与权限之间的关系
    * */
    public void updateUserGroupPermissions(Integer userGroupId, List<Integer> permissionsIds) {
        // 删除所有与该用户组相关的旧关系
        QueryWrapper<UserGroupPermissions> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_group_id", userGroupId);
        userGroupPermissionsMapper.delete(queryWrapper);

        // 根据新的数据创建新的关系
        for (Integer permissionId : permissionsIds) {
            UserGroupPermissions userGroupPermissions = new UserGroupPermissions();
            userGroupPermissions.setUserGroupId(userGroupId);
            userGroupPermissions.setPermissionsId(permissionId);
            userGroupPermissionsMapper.insert(userGroupPermissions);
        }
    }

}
