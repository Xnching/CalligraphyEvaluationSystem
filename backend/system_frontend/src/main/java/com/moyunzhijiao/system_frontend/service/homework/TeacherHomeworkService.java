package com.moyunzhijiao.system_frontend.service.homework;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.common.Constants;
import com.moyunzhijiao.system_frontend.controller.dto.homework.HomeworkDTO;
import com.moyunzhijiao.system_frontend.entity.homework.Homework;
import com.moyunzhijiao.system_frontend.entity.homework.TeacherHomework;
import com.moyunzhijiao.system_frontend.exception.ServiceException;
import com.moyunzhijiao.system_frontend.mapper.homework.TeacherHomeworkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherHomeworkService extends ServiceImpl<TeacherHomeworkMapper, TeacherHomework> {

    @Autowired
    TeacherHomeworkMapper teacherHomeworkMapper;
    @Autowired
    HomeworkSubmissionService homeworkSubmissionService;
    @Autowired
    HomeworkImageService homeworkImageService;

    /*
    * 教师的所有作业列表
    * */
    public IPage<HomeworkDTO> getHomeworkPageOfTeacher(IPage<HomeworkDTO> page, Integer type, Integer teacherId, String name) {
        Integer total;
        switch (type){
            case 1://未截止集体
                page = teacherHomeworkMapper.selectHomeworkPageOfKlass(page,teacherId,"集体",">",name);
                total = teacherHomeworkMapper.countHomeworkPageOfKlass(teacherId,"集体",">",name);
                total=total == null ? 0:total;
                page.setTotal(total);
                break;
            case 2://未截止个人
                page = teacherHomeworkMapper.selectHomeworkPageOfKlass(page,teacherId,"个人",">",name);
                total = teacherHomeworkMapper.countHomeworkPageOfKlass(teacherId,"个人",">",name);
                total=total == null ? 0:total;
                page.setTotal(total);
                break;
            case 3://已截止集体
                page = teacherHomeworkMapper.selectHomeworkPageOfKlass(page,teacherId,"集体","<",name);
                total = teacherHomeworkMapper.countHomeworkPageOfKlass(teacherId,"集体","<",name);
                total=total == null ? 0:total;
                page.setTotal(total);
                break;
            case 4://已截止个人
                page =teacherHomeworkMapper.selectHomeworkPageOfKlass(page,teacherId,"个人","<",name);
                total = teacherHomeworkMapper.countHomeworkPageOfKlass(teacherId,"个人","<",name);
                total=total == null ? 0:total;
                page.setTotal(total);
                break;
            case 5://未截止
                page =teacherHomeworkMapper.selectHomeworkPageOfKlass(page,teacherId,null,">",name);
                total = teacherHomeworkMapper.countHomeworkPageOfKlass(teacherId,null,">",name);
                total=total == null ? 0:total;
                page.setTotal(total);
                break;
            case 6://已截止
                page =teacherHomeworkMapper.selectHomeworkPageOfKlass(page,teacherId,null,"<",name);
                total = teacherHomeworkMapper.countHomeworkPageOfKlass(teacherId,null,"<",name);
                total=total == null ? 0:total;
                page.setTotal(total);
                break;
            default:
                throw new ServiceException(Constants.CODE_401,"type内容错误!");
        }
        //遍历每个HomeworkDTO对象,并设置unSubmit属性
        for (HomeworkDTO homeworkDTO : page.getRecords()) {
            //根据作业id查找所有没完成作业的
            Integer unSubmitCount =  homeworkSubmissionService.getNumberOfNotSubmit(homeworkDTO.getId());
            homeworkDTO.setUnSubmit(unSubmitCount);
        }
        return page;
    }

    public IPage<Homework> getAllHomeworkPageOfTeacher(IPage<Homework> page,Integer teacherId){
        page = teacherHomeworkMapper.selectAllHomeworkPageOfTeacher(page,teacherId);
        Integer total = teacherHomeworkMapper.countAllHomeworkPageOfTeacher(teacherId);
        total=total == null ? 0:total;
        page.setTotal(total);
        page.getRecords().forEach(homework -> {
            List<String> imageUrls = homeworkImageService.getUrlBatch(homework.getId());
            homework.setUrls(imageUrls);
        });
        return page;
    }

    /*
    * 增加教师和作业之间的联系
    * */
    public void addTeacherHomework(Integer teacherId, Integer homeworkId,Integer templateId,String templateType){
        //教师和作业之间添加联系
        TeacherHomework teacherHomework = new TeacherHomework();
        teacherHomework.setHomeworkId(homeworkId);
        teacherHomework.setTemplateId(templateId);
        teacherHomework.setTemplateType(templateType);
        teacherHomework.setTeacherId(teacherId);
        save(teacherHomework);
    }

    public void deleteByHomework(Integer homeworkId) {
        QueryWrapper<TeacherHomework> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("homework_id",homeworkId);
        teacherHomeworkMapper.delete(queryWrapper);
    }
}
