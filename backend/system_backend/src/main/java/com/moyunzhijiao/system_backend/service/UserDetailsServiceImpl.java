package com.moyunzhijiao.system_backend.service;

import cn.hutool.core.util.ObjectUtil;
import com.moyunzhijiao.system_backend.entiy.CustomUser;
import com.moyunzhijiao.system_backend.entiy.back.Permissions;
import com.moyunzhijiao.system_backend.entiy.back.User;
import com.moyunzhijiao.system_backend.service.back.PermissionsService;
import com.moyunzhijiao.system_backend.service.back.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/*
* spring security用到的方法
* */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserService userService;
    @Autowired
    PermissionsService permissionsService;
    /*
     * 加载用户数据的方法，例如相关信息和权限
     * */
    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        User user = userService.getByLoginId(loginId);
        if(ObjectUtil.isNull(user)){
            throw new UsernameNotFoundException("用户名不存在！");
        }
        //赋予权限
        List<String> userPermsList = permissionsService.getPermissions(user.getUserGroupId());
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String perm : userPermsList) {
            authorities.add(new SimpleGrantedAuthority(perm.trim()));
        }
        return new CustomUser(user, authorities);
    }

    /*
    * 赋予权限，主要用于过滤器中使用
    * */
    public List<SimpleGrantedAuthority> getPermission(String type,Integer userGroupId){
        List<String> userPermsList ;
        if(type.equals("教师")){
            userPermsList = new ArrayList<>();
            userPermsList.add(permissionsService.getReviewTeacherPermission());
        }else if(type.equals("系统用户")){
            userPermsList = permissionsService.getPermissions(userGroupId);
        }else
            userPermsList = new ArrayList<>();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String perm : userPermsList) {
            authorities.add(new SimpleGrantedAuthority(perm.trim()));
        }
        return authorities;
    }
}
