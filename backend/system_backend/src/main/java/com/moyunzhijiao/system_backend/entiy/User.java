package com.moyunzhijiao.system_backend.entiy;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
//此处可以使用@TableName 表名主界来指定当前实体类对应的表名，如下面的User实体类对应表名为user
@TableName(value="user")
public class User {
    //此处使用@TableId注解（标注在主键上）或可使用@TableField 注解（标注在其他成员属性上）来指定对应的字段名
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;
    String login_id;
    String name;
    String password;
    String phone;
}
