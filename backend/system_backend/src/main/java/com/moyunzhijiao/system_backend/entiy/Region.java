package com.moyunzhijiao.system_backend.entiy;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName(value="region")
public class Region {
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;
    String name;
    Integer level;
    @TableField(value="parent_id")
    Integer parentId;
    //在数据表中没有children这个字段，这个在做菜单的时候会用到，所以使用exist=false忽略
    @TableField(exist = false)
    private List<Region> children;
}
