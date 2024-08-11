package com.moyunzhijiao.system_frontend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.entity.Student;
import com.moyunzhijiao.system_frontend.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService extends ServiceImpl<StudentMapper, Student> {
    @Autowired
    StudentMapper studentMapper;
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
