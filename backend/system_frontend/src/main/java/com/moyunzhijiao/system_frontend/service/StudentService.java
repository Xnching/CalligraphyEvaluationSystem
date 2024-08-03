package com.moyunzhijiao.system_frontend.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.entity.Student;
import com.moyunzhijiao.system_frontend.mapper.StudentMapper;
import org.springframework.stereotype.Service;

@Service
public class StudentService extends ServiceImpl<StudentMapper, Student> {
}
