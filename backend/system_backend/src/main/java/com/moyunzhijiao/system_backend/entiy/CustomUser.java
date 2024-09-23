package com.moyunzhijiao.system_backend.entiy;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUser extends User {
    private com.moyunzhijiao.system_backend.entiy.back.User sysUser;
    public CustomUser(com.moyunzhijiao.system_backend.entiy.back.User sysUser, Collection<? extends GrantedAuthority> authorities) {
        super(String.valueOf(sysUser.getId()), sysUser.getPassword(), authorities);
        this.sysUser = sysUser;
    }
    public com.moyunzhijiao.system_backend.entiy.back.User getSysUser() {
        return sysUser;
    }
    public void setSysUser(com.moyunzhijiao.system_backend.entiy.back.User sysUser) {
        this.sysUser = sysUser;
    }
}