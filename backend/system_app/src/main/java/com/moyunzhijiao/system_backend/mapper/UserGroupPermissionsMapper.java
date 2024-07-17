package com.moyunzhijiao.system_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moyunzhijiao.system_backend.entiy.UserGroupPermissions;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserGroupPermissionsMapper extends BaseMapper<UserGroupPermissions> {
    @Select("select permissions_id from user_group_permissions where user_group_id =#{userGroupId}")
    List<Integer> selectByUserGroupId(@Param("userGroupId") Integer userGroupId);
}
