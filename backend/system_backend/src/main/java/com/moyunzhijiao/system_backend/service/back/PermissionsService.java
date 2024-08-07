package com.moyunzhijiao.system_backend.service.back;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.entiy.back.Permissions;
import com.moyunzhijiao.system_backend.mapper.back.PermissionsMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionsService extends ServiceImpl<PermissionsMapper, Permissions> {

    /*
    * 用于查出菜单
    * */
    public List<Permissions> findPermissions(String name){
        QueryWrapper<Permissions> queryWrapper=new QueryWrapper<>();
        if(StrUtil.isNotBlank(name)){
            queryWrapper.like("name",name);
        }
        List<Permissions> list = list(queryWrapper);
        List<Permissions> parentNodes=list.stream().filter(menu -> menu.getParentId()==null).collect(Collectors.toList());
        for(Permissions menu:parentNodes){
            menu.setChildren(getChildren(menu, list));
        }
        return parentNodes;
    }

    /*
    * 拼接子菜单和父菜单
    * */
    private List<Permissions> getChildren(Permissions parent, List<Permissions> allMenu) {
        List<Permissions> children = allMenu.stream().filter(menu -> menu.getParentId()!=null && menu.getParentId().equals(parent.getId())).collect(Collectors.toList());
        for (Permissions child : children) {
            List<Permissions> grandChildren = allMenu.stream().filter(menu -> menu.getParentId()!=null && menu.getParentId().equals(child.getId())).collect(Collectors.toList());
            child.setChildren(grandChildren);
        }
        return children;
    }


}
