package com.moyunzhijiao.system_backend.mapper.back;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_backend.controller.dto.back.UserDTO;
import com.moyunzhijiao.system_backend.entiy.back.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

//Mybatis-Plus里的BaseMapper接口，自带crud功能，继承了BaseMapper接口的接口
//（接口与接口之间是继承关系，可以单继承也可以多继承）直接可以调用增删改查方法而不用重新编写。
public interface UserMapper extends BaseMapper<User> {
    /*
    * 联合查询需要自定义sql语句，此处为用户表的分页查询以及模糊查询
    * */
    @Select("SELECT user.*, user_group.name AS userGroupName " +
            "   FROM user LEFT JOIN user_group ON user.user_group_id = user_group.id " +
            "WHERE user.delete_flag = 0 AND (user.login_id LIKE CONCAT('%',#{str},'%') " +
            "   OR user.name LIKE CONCAT('%',#{str},'%') " +
            "   OR user_group.name LIKE CONCAT('%',#{str},'%'))")
    IPage<UserDTO> selectUserWithGroupName(IPage<UserDTO> page,@Param("str") String str);

    /*
     * 此处为用户表的分页查询以及模糊查询的获取总数
     * */
    @Select("SELECT COUNT(*) " +
            "   FROM user LEFT JOIN user_group ON user.user_group_id = user_group.id " +
            "WHERE user.delete_flag = 0 AND (user.login_id LIKE CONCAT('%',#{str},'%') " +
            "   OR user.name LIKE CONCAT('%',#{str},'%') " +
            "   OR user_group.name LIKE CONCAT('%',#{str},'%'))")
    int countUserWithGroupName(@Param("str") String str);

    @Select("select * " +
            "   from user " +
            "where login_id = #{loginId}")
    User getByLoginId(String loginId);
}
