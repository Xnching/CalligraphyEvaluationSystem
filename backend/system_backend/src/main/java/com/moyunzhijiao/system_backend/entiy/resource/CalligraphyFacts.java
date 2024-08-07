package com.moyunzhijiao.system_backend.entiy.resource;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName(value="calligraphy_facts")
public class CalligraphyFacts {
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;
    String name;
    Integer level;
    @TableField(value="parent_id")
    Integer parentId;
    //在数据表中没有children这个字段，这个在做菜单的时候会用到，所以使用exist=false忽略
    @TableField(exist = false)
    private List<CalligraphyFacts> children;
    @TableField(value="updated_time")
    String updatedTime;
}
