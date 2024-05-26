package com.moyunzhijiao.system_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moyunzhijiao.system_backend.entiy.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

//这里只是做测试使用

//Mybatis-Plus里的BaseMapper接口，自带crud功能，继承了BaseMapper接口的接口
//（接口与接口之间是继承关系，可以单继承也可以多继承）直接可以调用增删改查方法而不用重新编写。
public interface UserMapper extends BaseMapper<User> {

    //以下为未使用Mybatis-Plus时所做的数据库操作
//    @Select("select * from user")
//    List<User> findAll();
//
//    //添加一个插入方法
//    @Insert("insert into user(name,password,email,phone,nickname,address) " +
//            "VALUES(#{username},#{password},#{email},#{phone},#{nickname},#{address});")
//    int insert(User user);
//
//    //添加的更新方法
//    @Update("update sys_user set username=#{username},password=#{password}," +
//            "nickname=#{nickname},email=#{email},phone=#{phone},address=#{address} where id=#{id}")
//    int update(User user);
//
//    //数据删除
//    @Delete ("delete from sys_user where id=#{id}")
//    int deleteById(@Param("id") Integer id);

}
