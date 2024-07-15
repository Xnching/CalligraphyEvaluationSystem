package com.moyunzhijiao.system_backend.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moyunzhijiao.system_backend.common.Constants;
import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.controller.dto.UserDTO;
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
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO){
        String login_id = userDTO.getLoginId();
        String password = userDTO.getPassword();
        //调用hutool工具中的StrUtil函数实现用户名和密码是否为空的判断
        if(StrUtil.isBlank(login_id) || StrUtil.isBlank(password)){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        UserDTO dto = userService.login(userDTO);
        return Result.success(dto);
    }


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
