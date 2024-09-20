package com.moyunzhijiao.system_frontend.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.common.Constants;
import com.moyunzhijiao.system_frontend.controller.dto.StudentDTO;
import com.moyunzhijiao.system_frontend.entity.Region;
import com.moyunzhijiao.system_frontend.entity.Student;
import com.moyunzhijiao.system_frontend.entity.Teacher;
import com.moyunzhijiao.system_frontend.entity.homework.HomeworkSubmission;
import com.moyunzhijiao.system_frontend.exception.ServiceException;
import com.moyunzhijiao.system_frontend.mapper.RegionMapper;
import com.moyunzhijiao.system_frontend.mapper.StudentMapper;
import com.moyunzhijiao.system_frontend.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService extends ServiceImpl<StudentMapper, Student> {
    @Autowired
    StudentMapper studentMapper;

    @Autowired
    RegionMapper regionMapper;
    @Autowired
    KlassService klassService;
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
            studentDTO.setRegion(this.getFullRegionName(studentDTO.getRegionId()));
            return studentDTO;
        }else {
            throw new ServiceException(Constants.CODE_600,"用户名或密码错误");
        }
    }

    public String getFullRegionName(String regionId) {
        if (regionId == null) {
            return "";
        }

        StringBuilder fullRegionName = new StringBuilder();
        Region currentRegion = regionMapper.selectById(regionId);

        while (currentRegion != null) {
            fullRegionName.insert(0, currentRegion.getName()); // 在开头插入当前地区名称
            if (currentRegion.getParentId() != null) {
                fullRegionName.insert(0, "-"); // 添加分隔符
            }
            currentRegion = regionMapper.selectById(currentRegion.getParentId()); // 获取上一级地区
        }

        return fullRegionName.toString();
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

    @Transactional
    public String updateAvatar(Integer teacherId, MultipartFile avatar) {
        Student student = studentMapper.selectById(teacherId);

        //找到旧的文件名
        String oldUrl = student.getPictureUrl();
        String oldFileName = StrUtil.subAfter(oldUrl, "/", true);
        // 获取项目根目录
        String avatarFilePath = ConfigService.getAvatarFilePath();
        String oldImageFilePath = avatarFilePath + oldFileName;
        File oldImageFile = new File(oldImageFilePath);
        //尝试删除旧的文件
        if (oldImageFile.exists()) {
            oldImageFile.delete();
        }
        //更新文件
        String newOriginalFileName = avatar.getOriginalFilename();
        String newImageFileName = UUID.randomUUID()+"-"+newOriginalFileName;
        String newImageFilePath = avatarFilePath+newImageFileName;
        //传给前端的后端的API接口
        String newImageFileUrl = ConfigService.getAvatarUrl()+"/"+newImageFileName;
        File newImageDest = new File(newImageFilePath);
        try {
            //保存文件
            avatar.transferTo(newImageDest);
            //保存数据到数据库
            student.setPictureUrl(newImageFileUrl);
            studentMapper.updateById(student);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException(Constants.CODE_500, "系统保存文件失败");
        }
        return student.getPictureUrl();
    }

    public Integer getStuIdByStuNo(Integer stuNo){
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_number",stuNo);
        return  studentMapper.selectOne(queryWrapper).getId();
    }

    /*
    * 获取一个教师的所有学生列表，其中名字模糊查询
    * */
    public List<StudentDTO> getAllByTeacher(Integer teacherId, String name) {
        List<Integer> klassIdList = klassService.getKlassIdByTeacher(teacherId);
        return studentMapper.selectAllByTeacher(klassIdList,name);
    }

    public List<StudentDTO> getByKlass(Integer classId, String name) {
        return studentMapper.selectByKlass(classId,name);
    }
}
