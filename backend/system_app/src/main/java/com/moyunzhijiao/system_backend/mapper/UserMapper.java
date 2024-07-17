package com.moyunzhijiao.system_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moyunzhijiao.system_backend.entiy.User;

//Mybatis-Plus里的BaseMapper接口，自带crud功能，继承了BaseMapper接口的接口
//（接口与接口之间是继承关系，可以单继承也可以多继承）直接可以调用增删改查方法而不用重新编写。
public interface UserMapper extends BaseMapper<User> {


}
