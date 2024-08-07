package com.moyunzhijiao.system_backend.service.back;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.controller.dto.back.UserGroupDTO;
import com.moyunzhijiao.system_backend.entiy.back.UserGroup;
import com.moyunzhijiao.system_backend.entiy.back.UserGroupPermissions;
import com.moyunzhijiao.system_backend.entiy.back.UserGroupWithPId;
import com.moyunzhijiao.system_backend.mapper.back.UserGroupMapper;
import com.moyunzhijiao.system_backend.mapper.back.UserGroupPermissionsMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserGroupService extends ServiceImpl<UserGroupMapper, UserGroup> {
    @Autowired
    UserGroupMapper userGroupMapper;
    @Resource
    UserGroupPermissionsMapper userGroupPermissionsMapper;
    @Resource
    UserGroupPermissionsService userGroupPermissionsService;
    /*
    * 获取所有用户组的id和名称用于渲染用户组下拉框
    * */
    public List<Map<String ,Object>>getIdAndName(){
        return userGroupMapper.selectIdAndName();
    }

    /*
    * 分页查询，同时把权限表也带过去
    * */
    public IPage<UserGroupDTO> selectPageWithGroupName(IPage<UserGroupDTO> page, String str) {
        List<UserGroupWithPId> userGroupsWithPermission = userGroupMapper.selectUserGroupWithPermissions(str);
        Map<Integer, UserGroupDTO> userGroupDTOMap = new HashMap<>();
        //根据用户组id把所有权限id合并起来
        for (UserGroupWithPId userGroupWithPermission : userGroupsWithPermission) {
            UserGroupDTO dto = userGroupDTOMap.get(userGroupWithPermission.getId());
            if (dto == null) {
                dto = new UserGroupDTO();
                dto.setId(userGroupWithPermission.getId());
                dto.setName(userGroupWithPermission.getName());
                dto.setState(userGroupWithPermission.getState());
                dto.setUserCount(userGroupWithPermission.getUserCount());
                dto.setDescription(userGroupWithPermission.getDescription());
                dto.setMenus(new ArrayList<>());
                userGroupDTOMap.put(userGroupWithPermission.getId(), dto);
            }
            dto.getMenus().add(userGroupWithPermission.getPermissionsId());
        }
        List<UserGroupDTO> userGroupDTOs = new ArrayList<>(userGroupDTOMap.values());
        //设置总数
        page.setTotal(userGroupDTOs.size());
        // 设置记录
        page.setRecords(userGroupDTOs);
        return page;
    }

    /*
    * 获取一个用户组的状态，例如已激活和未激活
    * */
    public String getState(Integer userGroupId){
        QueryWrapper<UserGroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",userGroupId);
        UserGroup userGroup = userGroupMapper.selectOne(queryWrapper);
        return userGroup.getState();
    }

    /*
     * 将UserGroup转化成UserGroupDTO,将数据库交互的实例类转化为与与前端交互的实例类
     * */
    private UserGroup convertToEntity(UserGroupDTO userGroupDTO){
        UserGroup userGroup = new UserGroup();
        BeanUtil.copyProperties(userGroupDTO,userGroup);
        return userGroup;
    }

    /*
    * 往用户组表里插入数据同时添加与权限的关系
    * */
    public void addUserGroup(UserGroupDTO userGroupDTO) {
        UserGroup userGroup = convertToEntity(userGroupDTO);
        userGroupMapper.insert(userGroup);
        Integer userGroupId = userGroup.getId();  // 获取生成的ID
        for (Integer permissionId : userGroupDTO.getMenus()) {
            UserGroupPermissions userGroupPermissions = new UserGroupPermissions();
            userGroupPermissions.setUserGroupId(userGroupId);
            userGroupPermissions.setPermissionsId(permissionId);
            userGroupPermissionsMapper.insert(userGroupPermissions);
        }
    }

    /*
     * 根据id逻辑删除一个用户数据，为逻辑删除
     * */
    public void deleteUserGroup(String id){
        UserGroup userGroup = userGroupMapper.selectById(id);
        userGroup.setDeleteFlag(true);
        userGroupMapper.updateById(userGroup);
    }

    /*
     * 根据id更新一个用户数据
     * */
    public void updateUserGroup(UserGroupDTO userGroupDTO){
        UserGroup userGroup = convertToEntity(userGroupDTO);
        userGroupMapper.updateById(userGroup);
        userGroupPermissionsService.updateUserGroupPermissions(userGroup.getId(),userGroupDTO.getMenus());
    }
}
