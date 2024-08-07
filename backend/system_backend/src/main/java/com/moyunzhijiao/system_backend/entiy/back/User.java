package com.moyunzhijiao.system_backend.entiy.back;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
    @TableField(value="login_id")   //这样处理的主要目的是java对带有下划线的字段不识别，所以改为驼峰形式
    String loginId;
    String name;
    String password;
    String phone;
    @TableField(value="user_group_id")
    Integer userGroupId;
    @TableField(value="delete_flag")
    boolean deleteFlag;
    @TableField(value="created_time")
    String createdTime;     //如果需要年月日格式的可以使用Date类型，如果需要具体到时分秒就使用String类型
}
