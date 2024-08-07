package com.moyunzhijiao.system_backend.service.front;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.controller.dto.front.TeacherDTO;
import com.moyunzhijiao.system_backend.entiy.front.Teacher;
import com.moyunzhijiao.system_backend.mapper.front.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherService extends ServiceImpl<TeacherMapper, Teacher> {

    @Autowired
    TeacherMapper teacherMapper;
    public Teacher convertToEntity(TeacherDTO teacherDTO){
        Teacher teacher = new Teacher();
        BeanUtil.copyProperties(teacherDTO,teacher);
        return teacher;
    }

    public TeacherDTO convertToDTO(Teacher teacher){
        TeacherDTO teacherDTO = new TeacherDTO();
        BeanUtil.copyProperties(teacher,teacherDTO);
        return teacherDTO;
    }

    public IPage<Teacher> selectPage(IPage<Teacher> page, Integer schoolId, String str) {
        LambdaQueryWrapper<Teacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teacher::getSchoolId, schoolId)
                .eq(Teacher::isDeleteFlag, false)
                .and(i -> i.like(Teacher::getName, str)
                        .or()
                        .like(Teacher::getWorkno, str));
        return this.page(page, queryWrapper);
    }

    public void deleteTeacher(String id) {
        Teacher teacher = teacherMapper.selectById(id);
        teacher.setDeleteFlag(true);
        teacherMapper.updateById(teacher);
    }

    @Transactional
    public void addTeacherBatch(Integer id, InputStream inputStream) {
        List<Teacher> teacherList = new ArrayList<>();
        EasyExcel.read(inputStream, Teacher.class, new AnalysisEventListener<Teacher>() {
            @Override
            public void invoke(Teacher data, AnalysisContext context) {
                data.setSchoolId(id);
                if ( StrUtil.isBlank(data.getPassword())) {
                    data.setPassword(data.getWorkno());
                }
                teacherList.add(data);
            }
            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
            }
        }).sheet().doRead();
        saveBatch(teacherList);
    }

    public void singleAdd(Teacher teacher) {
        if(StrUtil.isBlank(teacher.getPassword())){
            teacher.setPassword(teacher.getWorkno());
        }
        teacherMapper.insert(teacher);
    }
}
