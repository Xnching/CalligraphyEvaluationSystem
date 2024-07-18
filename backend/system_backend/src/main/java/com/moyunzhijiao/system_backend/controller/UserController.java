package com.moyunzhijiao.system_backend.controller;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyunzhijiao.system_backend.common.Constants;
import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.controller.dto.UserDTO;
import com.moyunzhijiao.system_backend.entiy.User;
import com.moyunzhijiao.system_backend.mapper.UserMapper;
import com.moyunzhijiao.system_backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/backend")
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

    @GetMapping("/user/page")
    public Result findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "")String str){
        IPage<UserDTO> page=new Page<>(pageNum,pageSize);
        page = userService.selectPageWithGroupName(page,str);
        return Result.success(page);
    }
}
