package com.moyunzhijiao.system_backend.entiy.back;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value="user_group")
public class UserGroup {
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;
    String name;
    String state;
    @TableField(value="user_count")
    Integer userCount;
    String description;
    @TableField(value="delete_flag")
    boolean deleteFlag;
    @TableField(value="created_time")
    String createTime;
    @TableField(exist = false)
    Integer permissionsId;
}
