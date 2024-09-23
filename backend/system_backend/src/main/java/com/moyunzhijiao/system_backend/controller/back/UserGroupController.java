package com.moyunzhijiao.system_backend.controller.back;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.controller.dto.back.UserGroupDTO;
import com.moyunzhijiao.system_backend.service.back.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/backend/user-group")
@PreAuthorize("hasAuthority('用户组管理')")
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
