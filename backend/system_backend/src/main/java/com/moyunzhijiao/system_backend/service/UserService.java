package com.moyunzhijiao.system_backend.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.entiy.User;
import com.moyunzhijiao.system_backend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper,User> {
    @Autowired
    private UserMapper userMapper;

    public Boolean saveUser(User user){

        return saveOrUpdate(user);
    }


//    //更新方法
//    public int save(User user){
//        //如果user没有id则表明是新增
//        if(user.getId()==null){
//            return userMapper.insert(user);
//        }
//        //否则就是更新
//        else {
//            return userMapper.update(user);
//        }
//    }

    //删除方法
    public Integer deleteById(Integer id){
        return userMapper.deleteById(id);
    }

}
