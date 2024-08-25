package com.moyunzhijiao.system_backend.service.front;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.common.Constants;
import com.moyunzhijiao.system_backend.controller.dto.base.GradeDTO;
import com.moyunzhijiao.system_backend.controller.dto.front.StudentDTO;
import com.moyunzhijiao.system_backend.controller.dto.listener.GenericDTOListener;
import com.moyunzhijiao.system_backend.entiy.front.Student;
import com.moyunzhijiao.system_backend.exception.ServiceException;
import com.moyunzhijiao.system_backend.mapper.front.StudentMapper;
import com.moyunzhijiao.system_backend.service.base.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentService extends ServiceImpl<StudentMapper, Student> {

    @Autowired
    StudentMapper studentMapper;
    @Autowired
    GradeService gradeService;
    @Autowired
    KlassService klassService;
    public IPage<StudentDTO> getStudentsByKlass(IPage<StudentDTO> page,Integer klassId){
        page = studentMapper.getStudentsByKlass(page,klassId);
        Integer count = studentMapper.countStudentByKlass(klassId);
        page.setTotal(count);
        return page;
    }

    public void deleteKlass(String id){
        Student student = studentMapper.selectById(id);
        student.setKlassId(0);
        studentMapper.updateById(student);
    }

    public IPage<StudentDTO> selectPage(IPage<StudentDTO> page, Integer schoolId, String str) {
        page = studentMapper.selectPage(page,schoolId,str);
        Integer count = studentMapper.countPage(schoolId,str);
        page.setTotal(count);
        return page;
    }

    public void updateByDTO(StudentDTO studentDTO) {
        Student student = convertToEntity(studentDTO);
        if(StrUtil.isBlank(student.getPassword())){
            student.setPassword(student.getStudentNumber());
        }
        updateById(student);
    }

    public Student convertToEntity(StudentDTO studentDTO){
        Student student = new Student();
        BeanUtil.copyProperties(studentDTO,student);
        return student;
    }

    public void deleteStudent(String id) {
        Student student = studentMapper.selectById(id);
        student.setDeleteFlag(true);
        studentMapper.updateById(student);
    }

    public void singleAdd(StudentDTO studentDTO) {
        Student student = convertToEntity(studentDTO);
        //有空白字符也会判定成功
        if(StrUtil.isBlank(student.getPassword())){
            student.setPassword(student.getStudentNumber());
        }
        studentMapper.insert(student);
    }

    @Transactional
    public void addKlassBatch(Integer id, InputStream inputStream) {
        //获取所有年级的姓名
        List<GradeDTO> grades = gradeService.getAllGrades();
        Set<String> gradesName = grades.stream().map(GradeDTO::getName).collect(Collectors.toSet());
        //把要用来做参照的年级发给监听器
        GenericDTOListener<StudentDTO> genericDTOListener = new GenericDTOListener<>(gradesName);
        //监听器一行一行校验
        EasyExcel.read(inputStream,StudentDTO.class,genericDTOListener).sheet().doRead();
        //获取excel文件内容
        List<StudentDTO> dtoList = genericDTOListener.getValidData();
        System.out.println("看看批dtoList有没有值："+dtoList.toString());
        // 设置每个StudentDTO的gradeId,schoolId和klassId并转换为Klass对象
        List<Student> studentList = dtoList.stream().map(studentDTO -> {
            //设置密码
            if(StrUtil.isBlank(studentDTO.getPassword())){
                studentDTO.setPassword(studentDTO.getStudentNumber());
            }
            Integer gradeId = gradeService.getIdByField(studentDTO.getGrade());
            studentDTO.setGradeId(gradeId);
            Integer klassId = klassService.getIdByField(studentDTO.getKlass(),studentDTO.getGradeId(),id);
            if(ObjectUtil.isNull(klassId)){
                throw new ServiceException(Constants.CODE_400,"文件中存在班级名不存在该学校或对应年级里。");
            }
            studentDTO.setKlassId(klassId);
            studentDTO.setSchoolId(id);
            Student student = convertToEntity(studentDTO);
            return student;
        }).collect(Collectors.toList());
        // 保存到数据库
        saveBatch(studentList);
    }
}
