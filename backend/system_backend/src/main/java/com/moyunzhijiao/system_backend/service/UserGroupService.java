package com.moyunzhijiao.system_backend.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.entiy.UserGroup;
import com.moyunzhijiao.system_backend.mapper.UserGroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserGroupService extends ServiceImpl<UserGroupMapper, UserGroup> {
    @Autowired
    UserGroupMapper userGroupMapper;
    public List<Map<String ,Object>>getIdAndName(){
        return userGroupMapper.selectIdAndName();
    }
}
