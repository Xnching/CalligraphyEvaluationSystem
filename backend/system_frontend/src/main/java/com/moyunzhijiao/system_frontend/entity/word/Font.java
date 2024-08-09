package com.moyunzhijiao.system_frontend.entity.word;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Font {
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;
    String name;
}
