package com.moyunzhijiao.system_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_backend.entiy.UserGroup;
import com.moyunzhijiao.system_backend.entiy.UserGroupWithPId;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface UserGroupMapper extends BaseMapper<UserGroup> {

    /*  根据id查找状态
    * */
    @Select("select id , name from user_group where state ='已激活' ")
    List<Map<String ,Object>>selectIdAndName();

    /*  用户组分页查询和模糊查询
    * */
    @Select("SELECT * " +
            "FROM user_group " +
            "WHERE delete_flag = 0 AND (user_group.state LIKE CONCAT('%',#{str},'%') " +
            "OR user_group.name LIKE CONCAT('%',#{str},'%') )")
    List<UserGroup> selectUserGroup(String str);

    /*  用户组分页查询和模糊查询获取总数
     * */
    @Select("SELECT COUNT(*) " +
            "FROM user_group " +
            "WHERE delete_flag = 0 AND (user_group.name LIKE CONCAT('%',#{str},'%') " +
            "OR user_group.state LIKE CONCAT('%',#{str},'%') ) ")
    Integer countUserGroup(String str);

    /*
    *
    * */
    @Select("SELECT ug.*, ugp.permissions_id " +
            "FROM user_group ug " +
            "LEFT JOIN user_group_permissions ugp ON ug.id = ugp.user_group_id " +
            "WHERE ug.delete_flag = 0 AND (ug.state LIKE CONCAT('%',#{str},'%') " +
            "OR ug.name LIKE CONCAT('%',#{str},'%') )")
    List<UserGroupWithPId> selectUserGroupWithPermissions(String str);


}
