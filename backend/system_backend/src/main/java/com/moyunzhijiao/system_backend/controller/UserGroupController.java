package com.moyunzhijiao.system_backend.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.controller.dto.UserDTO;
import com.moyunzhijiao.system_backend.controller.dto.UserGroupDTO;
import com.moyunzhijiao.system_backend.entiy.UserGroup;
import com.moyunzhijiao.system_backend.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/backend/user-group")
public class UserGroupController {

    @Autowired
    private UserGroupService userGroupService;
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "")String str){
        IPage<UserGroupDTO> page=new Page<>(pageNum,pageSize);
        page = userGroupService.selectPageWithGroupName(page,str);
        return Result.success(page);
    }

    /*
    * 增加一个用户组以及对应权限
    * */
    @PostMapping("/add")
    public Result addUserGroupAndPermissions(@RequestBody UserGroupDTO userGroupDTO){
        userGroupService.addUserGroup(userGroupDTO);
        return Result.success();
    }

    /*
     * 删除一行用户数据
     * */
    @PutMapping("/delete")
    public Result deleteUserGroup(@RequestBody Map<String, String> params){
        String id = params.get("id");
        userGroupService.deleteUserGroup(id);
        return Result.success();
    }


    /*
     * 编辑弹窗中的更新用户数据
     * */
    @PutMapping("/update")
    public Result updateUser(@RequestBody UserGroupDTO userGroupDTO){
        userGroupService.updateUserGroup(userGroupDTO);
        return Result.success();
    }
}
