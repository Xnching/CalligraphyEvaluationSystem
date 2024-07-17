package com.moyunzhijiao.system_backend.entiy;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value="user_group_permissions")
public class UserGroupPermissions {
    @TableField(value="user_group_id")
    Integer userGroupId;
    @TableField(value="permissions_id")
    Integer permissionsId;
    @TableField(value="region_id")
    Integer regionId;
    @TableField(value="updated_time")
    String updatedTime;
}
