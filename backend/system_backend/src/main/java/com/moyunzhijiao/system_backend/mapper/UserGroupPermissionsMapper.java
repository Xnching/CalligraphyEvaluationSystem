package com.moyunzhijiao.system_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moyunzhijiao.system_backend.controller.dto.PermissionsDTO;
import com.moyunzhijiao.system_backend.entiy.UserGroupPermissions;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserGroupPermissionsMapper extends BaseMapper<UserGroupPermissions> {



    /*
    * 查找一个用户组的全部权限id
    * */
    @Select("select permissions_id from user_group_permissions where user_group_id =#{userGroupId}")
    List<Integer> selectIdById(@Param("userGroupId") Integer userGroupId);

    /*
    *
    * */
    @Select("SELECT p.id, p.name " +
            "FROM user_group_permissions ugp " +
            "JOIN permissions p ON ugp.permissions_id = p.id " +
            "WHERE ugp.user_group_id = #{userGroupId} ")
    List<PermissionsDTO> selectIdAndNameById(@Param("userGroupId") Integer userGroupId);
}
