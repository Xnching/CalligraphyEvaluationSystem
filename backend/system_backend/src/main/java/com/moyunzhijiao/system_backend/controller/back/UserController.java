package com.moyunzhijiao.system_backend.controller.back;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyunzhijiao.system_backend.common.Constants;
import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.controller.dto.back.UserDTO;
import com.moyunzhijiao.system_backend.service.back.UserGroupService;
import com.moyunzhijiao.system_backend.service.back.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/backend")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserGroupService userGroupService;

    /*
    * 登录接口
    * */
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

    /*
    * 系统用户页面表的分页查询
    * */
    @GetMapping("/user/page")
    public Result findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "")String str){
        IPage<UserDTO> page=new Page<>(pageNum,pageSize);
        page = userService.selectPageWithGroupName(page,str);
        return Result.success(page);
    }

    /*
    * 获取所有用户组名称和id
    * */
    @GetMapping("/user/groups")
    public Result getGroups(){
        return Result.success(userGroupService.getIdAndName());
    }

    /*
    * 编辑弹窗中的更新用户数据
    * */
    @PutMapping("/user/update")
    public Result updateUser(@RequestBody UserDTO userDTO){
        userService.updateUser(userDTO);
        return Result.success();
    }

    /*
    * 删除一行用户数据
    * */
    @PutMapping("/user/delete")
    public Result deleteUser(@RequestBody Map<String, String> params){
        String id = params.get("id");
        userService.deleteUser(id);
        return Result.success();
    }

    /*
    * 新增一个用户
    * */
    @PostMapping("/user/add")
    public Result addUser(@RequestBody UserDTO userDTO){
        userService.addUser(userDTO);
        return Result.success();
    }
}
