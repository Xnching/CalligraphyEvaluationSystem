package com.moyunzhijiao.system_frontend.entity.StudentHomepage;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

public class Article {
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;
    String name;
    Integer first_type_id ;
    Integer second_type_id;
    String  tag;
    Boolean is_recommended;
    String picture_url;
    String created_time;
    String importer;
}
