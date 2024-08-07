package com.moyunzhijiao.system_backend.entiy.word;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value="structure")
public class Structure {
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;
    String name;
    @TableField(value="updated_time")
    String updatedTime;
}
