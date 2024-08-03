package com.moyunzhijiao.system_backend.entiy;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value="user_group_permissions")
public class UserGroupPermissions {
    @MppMultiId
    @TableField(value="user_group_id")
    Integer userGroupId;
    @MppMultiId
    @TableField(value="permissions_id")
    Integer permissionsId;
    @TableField(value="region_id")
    Integer regionId;
    @TableField(value="updated_time")
    String updatedTime;
}
