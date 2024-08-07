package com.moyunzhijiao.system_backend.service.back;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.common.Constants;
import com.moyunzhijiao.system_backend.controller.dto.back.UserDTO;
import com.moyunzhijiao.system_backend.entiy.back.Permissions;
import com.moyunzhijiao.system_backend.entiy.back.User;
import com.moyunzhijiao.system_backend.exception.ServiceException;
import com.moyunzhijiao.system_backend.mapper.back.UserMapper;
import com.moyunzhijiao.system_backend.utils.TokenUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private UserGroupPermissionsService userGroupPermissionsService;

    /*
     * 登陆方法，实现登录
     *
     **/
    public UserDTO login(UserDTO userDTO){
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
            //判断下所属用户组是否激活
            if (StrUtil.equals(userGroupService.getState(userGroupId),"未激活")){
                userDTO.setMenus(null);
            }else {
                //根据权限设置用户的菜单列表
                List<Permissions> menus = getMenus(userGroupId);
                userDTO.setMenus(menus);
            }
            return userDTO;     //返回登录类userDTO
        }else {
            throw new ServiceException(Constants.CODE_600,"用户名或密码错误");
        }
    }


    public Boolean saveUser(User user){
        return saveOrUpdate(user);
    }


    /*
     * 获取当前用户的菜单列表,此处不用表连接查询而是在后端筛选不推荐使用
     * 要尽可能地减少数据库查询次数和优化数据处理，
     * 数据库引擎可以优化查询并减少网络传输的数据量。
     */
    private List<Permissions> getMenus(Integer userGroupId){
        //根据用户组id查到所有权限的id
        List<Integer> permissionsIds = userGroupPermissionsService.getIdByUserGroupId(userGroupId);
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

    /*
    * 根据id更新一个用户数据
    * */
    public void updateUser(UserDTO userDTO){
        User user = convertToEntity(userDTO);
        userMapper.updateById(user);
    }

    /*
    * 将UserDTO转化成User,将与前端交互的实例类转化为与数据库交互的实例类
    * */
    private User convertToEntity(UserDTO userDTO){
        User user = new User();
        BeanUtil.copyProperties(userDTO,user);
        return user;
    }

    /*
     * 根据id逻辑删除一个用户数据，为逻辑删除
     * */
    public void deleteUser(String id){
        User user = userMapper.selectById(id);
        user.setDeleteFlag(true);
        userMapper.updateById(user);
    }

    /*
    * 新增一个用户
    * */
    public void addUser(UserDTO userDTO){
        User user = convertToEntity(userDTO);
        userMapper.insert(user);
    }


    public String getNameById(String id){
        User user = getById(id);
        if (user != null) {
            return user.getName();
        } else {
            return null;
        }
    }
}
