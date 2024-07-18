package com.moyunzhijiao.system_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moyunzhijiao.system_backend.entiy.UserGroup;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface UserGroupMapper extends BaseMapper<UserGroup> {
    @Select("select id , name from user_group where state ='已激活' ")
    List<Map<String ,Object>>selectIdAndName();
}
