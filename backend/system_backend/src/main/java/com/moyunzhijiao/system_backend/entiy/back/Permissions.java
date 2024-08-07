package com.moyunzhijiao.system_backend.entiy.back;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/*
* 权限即面板表，
* */
@Data
@TableName(value="permissions")
public class Permissions {
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;
    String name;
    String path;
    @TableField(value="parent_id")
    Integer parentId;
    @TableField(value="updated_time")
    String updatedTime;
    @TableField(value="page_path")
    String pagePath;
    //在数据表中没有children这个字段，这个在做菜单的时候会用到，所以使用exist=false忽略
    @TableField(exist = false)
    private List<Permissions> children;
}
