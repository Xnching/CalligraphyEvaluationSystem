package com.moyunzhijiao.system_backend.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.common.Constants;
import com.moyunzhijiao.system_backend.controller.dto.StudentDTO;
import com.moyunzhijiao.system_backend.entiy.Student;
import com.moyunzhijiao.system_backend.exception.ServiceException;
import com.moyunzhijiao.system_backend.mapper.StudentMapper;
import com.moyunzhijiao.system_backend.utils.TokenUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService extends ServiceImpl<StudentMapper, Student> {

    @Autowired
    private StudentMapper studentMapper;
    /*
     * 登陆方法，实现登录
     *
     **/
    public StudentDTO login(StudentDTO studentDTO){
        QueryWrapper<Student> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("login_id",studentDTO.getLoginId());
        queryWrapper.eq("password",studentDTO.getPassword());
        Student one;
        try {
            one = getOne(queryWrapper);
        } catch (Exception e) {
            //这里假设查询了多于1条记录，就让他报系统错误
            e.printStackTrace();
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
        if(one!=null){  //以下是登录判断业务
            //hutool里的
            BeanUtil.copyProperties(one,studentDTO,true);
            //设置token
            String token= TokenUtils.genToken(one.getId().toString(),one.getPassword().toString());
            studentDTO.setToken(token);
            return studentDTO;     //返回登录类studentDTO
        }else {
            throw new ServiceException(Constants.CODE_600,"用户名或密码错误");
        }
    }


    public Boolean savestudent(Student student){
        return saveOrUpdate(student);
    }


    

}
