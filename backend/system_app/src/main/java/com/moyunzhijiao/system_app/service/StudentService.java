package com.moyunzhijiao.system_app.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_app.common.Constants;
import com.moyunzhijiao.system_app.controller.dto.StudentDTO;
import com.moyunzhijiao.system_app.entity.Student;
import com.moyunzhijiao.system_app.exception.ServiceException;
import com.moyunzhijiao.system_app.mapper.StudentMapper;
import com.moyunzhijiao.system_app.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService extends ServiceImpl<StudentMapper, Student> {
    @Autowired
    StudentMapper studentMapper;

    public StudentDTO login(String name, String userPassword) {
        //使用mybatis-plus查询数据库
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getName,name);
        queryWrapper.eq(Student::getPassword,userPassword);
        Student one;
        try {
            //接口里的函数，mapper里也有许多写好的数据库操作可直接studentMapper.来调用
            one = getOne(queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
        if(one!=null){
            StudentDTO studentDTO = new StudentDTO();
            //使用hutool工具赋值类内相同名称的变量的数据
            BeanUtil.copyProperties(one,studentDTO);
            //设置token
            String token = TokenUtils.checkToken(studentDTO.getToken(),one.getId().toString(),one.getPassword());
            studentDTO.setToken(token);
            return studentDTO;
        }else {
            throw new ServiceException(Constants.CODE_600,"用户名或密码错误");
        }
    }
}
