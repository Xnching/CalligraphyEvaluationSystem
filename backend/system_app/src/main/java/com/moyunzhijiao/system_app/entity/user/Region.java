package com.moyunzhijiao.system_app.entity.user;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("region")
public class Region {
    Integer id;
    String name;
}
