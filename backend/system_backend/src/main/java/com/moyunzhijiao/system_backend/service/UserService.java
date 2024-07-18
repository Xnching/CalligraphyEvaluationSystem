package com.moyunzhijiao.system_backend.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.common.Constants;
import com.moyunzhijiao.system_backend.controller.dto.UserDTO;
import com.moyunzhijiao.system_backend.entiy.Permissions;
import com.moyunzhijiao.system_backend.entiy.User;
import com.moyunzhijiao.system_backend.entiy.UserGroup;
import com.moyunzhijiao.system_backend.exception.ServiceException;
import com.moyunzhijiao.system_backend.mapper.PermissionsMapper;
import com.moyunzhijiao.system_backend.mapper.UserGroupPermissionsMapper;
import com.moyunzhijiao.system_backend.mapper.UserMapper;
import com.moyunzhijiao.system_backend.utils.TokenUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserService extends ServiceImpl<UserMapper,User> {

    @Autowired
    private UserMapper userMapper;
    @Resource
    private UserGroupService userGroupService;
    @Resource
    private PermissionsService permissionsService;
    @Resource
    private UserGroupPermissionsMapper userGroupPermissionsMapper;
    /*
     * 登陆方法，实现登录
     *
     **/
    public UserDTO login(UserDTO userDTO){
        System.out.println(userDTO.getToken());
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("login_id",userDTO.getLoginId());
        queryWrapper.eq("password",userDTO.getPassword());
        User one;
        try {
            one = getOne(queryWrapper);
        } catch (Exception e) {
            //这里假设查询了多于1条记录，就让他报系统错误
            e.printStackTrace();
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
        if(one!=null){  //以下是登录判断业务
            //hutool里的
            BeanUtil.copyProperties(one,userDTO,true);
            //设置token
            String token= TokenUtils.checkToken(userDTO.getToken(),one.getId().toString(),one.getPassword());
            userDTO.setToken(token);
            Integer userGroupId = one.getUserGroupId();
            //根据权限设置用户的菜单列表
            List<Permissions> menus = getMenus(userGroupId);
            userDTO.setMenus(menus);
            return userDTO;     //返回登录类userDTO
        }else {
            throw new ServiceException(Constants.CODE_600,"用户名或密码错误");
        }
    }


    public Boolean saveUser(User user){
        return saveOrUpdate(user);
    }


    /*
     * 获取当前用户的菜单列表
     */
    private List<Permissions> getMenus(Integer userGroupId){
        //根据用户组id查到所有权限的id
        List<Integer> permissionsIds = userGroupPermissionsMapper.selectByUserGroupId(userGroupId);
        //查出系统所有菜单
        List<Permissions> menus=permissionsService.findPermissions("");
        //筛选当前用户菜单
        List<Permissions> ownMenus=new ArrayList<>();
        for (Permissions menu:menus){
            if(permissionsIds.contains(menu.getId())){
                ownMenus.add(menu);
            }
            List<Permissions> children = menu.getChildren();
            //removeIf移除children里面不在permissionsIds集合中的元素
            children.removeIf(child->!permissionsIds.contains(child.getId()));
        }
        return ownMenus;
    }


    /*
    * 分页查询
    * */
    public IPage<UserDTO> selectPageWithGroupName(IPage<UserDTO> page, String str) {
        page = userMapper.selectUserWithGroupName(page,str);
        Integer count = userMapper.countUserWithGroupName(str);
        page.setTotal(count);
        return page;
    }


}
