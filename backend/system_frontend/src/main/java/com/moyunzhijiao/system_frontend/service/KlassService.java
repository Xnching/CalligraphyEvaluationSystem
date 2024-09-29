package com.moyunzhijiao.system_frontend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.common.Constants;
import com.moyunzhijiao.system_frontend.controller.dto.KlassDTO;
import com.moyunzhijiao.system_frontend.controller.dto.KlassDetailDTO;
import com.moyunzhijiao.system_frontend.entity.homework.Homework;
import com.moyunzhijiao.system_frontend.entity.Klass;
import com.moyunzhijiao.system_frontend.entity.Student;
import com.moyunzhijiao.system_frontend.exception.ServiceException;
import com.moyunzhijiao.system_frontend.mapper.KlassMapper;
import com.moyunzhijiao.system_frontend.service.homework.HomeworkService;
import com.moyunzhijiao.system_frontend.service.homework.KlassHomeworkService;
import com.moyunzhijiao.system_frontend.service.homework.TeacherHomeworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KlassService extends ServiceImpl<KlassMapper, Klass> {
    @Autowired
    KlassMapper klassMapper;
    @Autowired
    StudentService studentService;
    @Autowired
    HomeworkService homeworkService;
    @Autowired
    TeacherHomeworkService teacherHomeworkService;

    @Autowired
    KlassHomeworkService klassHomeworkService;

    private KlassDTO convertToDTO(Klass klass){
        KlassDTO klassDTO = new KlassDTO();
        klassDTO.setId(klass.getId());
        klassDTO.setYear(klass.getYear());
        return klassDTO;
    }

    public IPage<KlassDTO> getKlassByTeacher(Integer type, Integer currentPage,Integer pageSize,Integer teacherId,Integer schoolId, String grade, String klass,String year) {
        IPage<KlassDTO> page =new Page<>(currentPage,pageSize);
        Integer total;
        switch (type){      //1已添加，0未添加
            case 1:
                page = klassMapper.selectKlassByTeacherOwn(page,teacherId,schoolId,grade,klass,year);
                total = klassMapper.countKlassByTeacherOwn(teacherId,schoolId,grade,klass,year);
                page.setTotal(total);       //有时候total需要判null
                break;
            case 0:
                page = klassMapper.selectKlassByTeacherNotOwn(page,teacherId,schoolId,grade,klass,year);
                 total = klassMapper.countKlassByTeacherNotOwn(teacherId,schoolId,grade,klass,year);
                page.setTotal(total);
                break;
            default:
                throw new ServiceException(Constants.CODE_401,"type内容错误！");
        }
        return page;
    }

    /*
     * 删除一个教师教授的班级
     * */
    public void deleteKlassOfTeacher(Integer klassId) {
        Klass klass = klassMapper.selectById(klassId);
        klass.setTeacherId(0);
        klassMapper.updateById(klass);
    }

    /*
    * 添加一个教师教授的班级
    * */
    public void addKlassOfTeacher(Integer teacherId, Integer klassId) {
        Klass klass = klassMapper.selectById(klassId);
        klass.setTeacherId(teacherId);
        klassMapper.updateById(klass);
    }

    /*
    * 教师的获取班级详情，其中有学生列表有作业列表和班级的信息
    * */
    public KlassDetailDTO getKlassDetaiList(Integer classId, Integer currentPage, Integer pageSize) {
        //设置查询条件
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "name", "gender","student_number");
        queryWrapper.eq("klass_id", classId);

        IPage<Student> studentPage = new Page<>(currentPage, pageSize);
        IPage<Student> resultPage = studentService.page(studentPage, queryWrapper);

        //转换结果
        IPage<KlassDetailDTO.Stu> stuPage = resultPage.convert(student -> {
            KlassDetailDTO.Stu stu = new KlassDetailDTO.Stu();
            stu.setStuId(student.getId());
            stu.setName(student.getName());
            stu.setGender(student.getGender());
            stu.setStuno(student.getStudentNumber());
            return stu;
        });

        //设置学生分页查询结果
        KlassDetailDTO klassDetailDTO = new KlassDetailDTO();
        klassDetailDTO.setPage(stuPage);

        //查询已截止作业列表
        List<Homework> list = klassHomeworkService.getDeadlineHomeworkByKlass(classId);
        klassDetailDTO.setDeadlineList(list);
        return klassDetailDTO;
    }

    public KlassDTO getKlassById(Integer id){
        KlassDTO klassDTO=klassMapper.getKlassById(id);
        return klassDTO;
    }

    public List<Integer> getKlassIdByTeacher(Integer teacherId){
        QueryWrapper<Klass> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id");
        queryWrapper.eq("teacher_id",teacherId);
        return  list(queryWrapper).stream().map(Klass::getId).collect(Collectors.toList());
    }
}
