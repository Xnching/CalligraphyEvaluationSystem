package com.moyunzhijiao.system_backend.controller;

import com.moyunzhijiao.system_backend.entiy.User;
import com.moyunzhijiao.system_backend.mapper.UserMapper;
import com.moyunzhijiao.system_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/backend")
public class UserController {
    @Autowired
    private UserMapper userMapper;
    private UserService userService;

    @PostMapping
    //此处是使用mybatis-plus,返回的是boolean类型
    public Boolean save(@RequestBody User user){
        return userService.saveUser(user);
    }

    //此处是使用mybtis-plus实现删除
    @DeleteMapping("/{id}")
    public boolean deleteById(@PathVariable Integer id){
        return  userService.removeById(id);
    }

    //使用mybtis-plus实现查询所有数据
    @GetMapping("/")
    public List<User> findAll(){
        return userService.list();
    }

    //使用mybtis-plus实现根据ID查找记录
    @GetMapping("/{id}")
    public User findOne(@PathVariable Integer id){
        return userService.getById(id);
    }


    //UserController类中添加save接口
    //@PostMapping
    //这里做了一个单纯的添加的示例，使用的是mapper中的insert方法
//    public int save(@RequestBody User user){
//        return userService.save(user);
//    }


    /*
    //旧的删除数据方法deleteById
    @DeleteMapping("/{id}")
    public Integer deleteById(@PathVariable Integer id){
        return userService.deleteById(id);
    }
     */
}
