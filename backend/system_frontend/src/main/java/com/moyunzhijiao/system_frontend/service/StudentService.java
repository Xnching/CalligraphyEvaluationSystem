package com.moyunzhijiao.system_frontend.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.common.Constants;
import com.moyunzhijiao.system_frontend.controller.dto.StudentDTO;
import com.moyunzhijiao.system_frontend.entity.Student;
import com.moyunzhijiao.system_frontend.entity.Teacher;
import com.moyunzhijiao.system_frontend.exception.ServiceException;
import com.moyunzhijiao.system_frontend.mapper.StudentMapper;
import com.moyunzhijiao.system_frontend.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService extends ServiceImpl<StudentMapper, Student> {
    @Autowired
    StudentMapper studentMapper;
    public StudentDTO login(StudentDTO studentDTO){
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_number",studentDTO.getStuno());
        queryWrapper.eq("password",studentDTO.getPassword());
        Student one;
        try{
            one =getOne(queryWrapper);
        }catch(Exception e){
            e.printStackTrace();
            throw new ServiceException(Constants.CODE_500,"参数错误，存在多个对象");
        }
        if(one!=null){
            BeanUtil.copyProperties(one,studentDTO);
            String token = TokenUtils.checkToken(studentDTO.getToken(),one.getId().toString(),"学生",one.getPassword());
            studentDTO.setToken(token);
            return studentDTO;
        }else {
            throw new ServiceException(Constants.CODE_600,"用户名或密码错误");
        }
    }

    /*
    * 根据班级获取学生
    * */
    public List<Student> getByKlass(Integer klassId, String stuInfo) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("klass_id",klassId);
        queryWrapper.like("name",stuInfo);
        return list(queryWrapper);
    }

    /*
    * 根据班级id列表获取学生id列表
    * */
    public List<Integer> getByKlassList(List<Integer> klassIdList) {
        List<Student> students;
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("klass_id", klassIdList);
        students = studentMapper.selectList(queryWrapper);

        // 提取学生的 ID 列表
        return students.stream()
                .map(Student::getId)
                .collect(Collectors.toList());
    }

}
