package com.moyunzhijiao.system_backend.service.front;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.common.Constants;
import com.moyunzhijiao.system_backend.controller.dto.back.UserDTO;
import com.moyunzhijiao.system_backend.controller.dto.front.TeacherDTO;
import com.moyunzhijiao.system_backend.entiy.back.Permissions;
import com.moyunzhijiao.system_backend.entiy.competition.Reviewers;
import com.moyunzhijiao.system_backend.entiy.front.Teacher;
import com.moyunzhijiao.system_backend.exception.ServiceException;
import com.moyunzhijiao.system_backend.mapper.back.PermissionsMapper;
import com.moyunzhijiao.system_backend.mapper.front.TeacherMapper;
import com.moyunzhijiao.system_backend.service.competition.ReviewersService;
import com.moyunzhijiao.system_backend.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherService extends ServiceImpl<TeacherMapper, Teacher> {

    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    ReviewersService reviewersMapper;
    @Autowired
    PermissionsMapper permissionsMapper;
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

    /*
    * 为了添加评阅教师，获取所有的教师
    * */
    public IPage<TeacherDTO> getAllTeacher(IPage<TeacherDTO> page, String str) {
        page = teacherMapper.selectAllTeacher(page,str);
        Long total = teacherMapper.countAllTeacher(str);
        page.setTotal(total);
        return page;
    }
    /*
     * 为了添加评阅教师到一个组别中，获取所有的教师
     * */
    public IPage<TeacherDTO> getAllTeacherInDivision(IPage<TeacherDTO> page, String str,Integer divisionId) {
        page = teacherMapper.selectAllTeacherInDivision(page,str,divisionId);
        Long total = teacherMapper.countAllTeacherInDivision(str,divisionId);
        page.setTotal(total);
        return page;
    }

    public Teacher login(Teacher teacher) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone",teacher.getPhone());
        queryWrapper.eq("password",teacher.getPassword());
        Teacher one;
        try {
            one = getOne(queryWrapper);
        } catch (Exception e) {
            //这里假设查询了多于1条记录，就让他报系统错误
            e.printStackTrace();
            throw new ServiceException(Constants.CODE_500,"参数错误，存在多个对象");
        }
        if(one!=null){
            Integer teacherId = one.getId();
            QueryWrapper<Reviewers> reviewersQueryWrapper = new QueryWrapper<>();
            queryWrapper.eq("teacher_id",teacherId);
            long count = reviewersMapper.count(reviewersQueryWrapper);
            if(count>0){
                BeanUtil.copyProperties(one,teacher);
                String token = TokenUtils.checkToken(teacher.getToken(),one.getId().toString(),"教师",one.getPassword());
                teacher.setToken(token);
                teacher.setMenus(getTeacherMenus());
                return teacher;
            }else {
                throw new ServiceException(Constants.CODE_400,"您不在评阅组中！");
            }
        }else {
            throw new ServiceException(Constants.CODE_600,"用户名或密码错误");
        }
    }

    /*
    * 评阅教师的面板只有评阅作品
    * */
    private List<Permissions> getTeacherMenus() {
        QueryWrapper<Permissions> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name","竞赛管理");
        Permissions father = permissionsMapper.selectOne(queryWrapper);
        QueryWrapper<Permissions> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("name","评委评分");
        Permissions child = permissionsMapper.selectOne(queryWrapper2);
        List<Permissions> children = new ArrayList<>();
        children.add(child);
        father.setChildren(children);
        List<Permissions> fathers = new ArrayList<>();
        fathers.add(father);
        return fathers;
    }
}
