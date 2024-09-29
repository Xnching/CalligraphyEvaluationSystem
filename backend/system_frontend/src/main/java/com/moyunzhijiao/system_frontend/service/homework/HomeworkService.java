package com.moyunzhijiao.system_frontend.service.homework;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.common.Constants;
import com.moyunzhijiao.system_frontend.controller.dto.KlassHomeworkDetailDTO;
import com.moyunzhijiao.system_frontend.controller.dto.PublishByTemplateDTO;
import com.moyunzhijiao.system_frontend.controller.dto.StudentDTO;
import com.moyunzhijiao.system_frontend.controller.dto.homework.HomeworkDTO;
import com.moyunzhijiao.system_frontend.entity.Copybook;
import com.moyunzhijiao.system_frontend.entity.Klass;
import com.moyunzhijiao.system_frontend.entity.Student;
import com.moyunzhijiao.system_frontend.entity.homework.Homework;
import com.moyunzhijiao.system_frontend.entity.homework.HomeworkImage;
import com.moyunzhijiao.system_frontend.entity.homework.TeacherHomework;
import com.moyunzhijiao.system_frontend.entity.template.CustomTemplate;
import com.moyunzhijiao.system_frontend.entity.template.CustomTemplateImage;
import com.moyunzhijiao.system_frontend.entity.template.SystemTemplate;
import com.moyunzhijiao.system_frontend.entity.template.SystemTemplateImage;
import com.moyunzhijiao.system_frontend.exception.ServiceException;
import com.moyunzhijiao.system_frontend.mapper.homework.HomeworkMapper;
import com.moyunzhijiao.system_frontend.mapper.homework.TeacherHomeworkMapper;
import com.moyunzhijiao.system_frontend.service.*;
import com.moyunzhijiao.system_frontend.service.note.KlassNoteReceiveService;
import com.moyunzhijiao.system_frontend.service.note.NoteService;
import com.moyunzhijiao.system_frontend.service.note.StudentNoteReceiveService;
import com.moyunzhijiao.system_frontend.service.outstanding.OutstandingHomeworkService;
import com.moyunzhijiao.system_frontend.service.template.CustomTemplateImageService;
import com.moyunzhijiao.system_frontend.service.template.CustomTemplateService;
import com.moyunzhijiao.system_frontend.service.template.SystemTemplateImageService;
import com.moyunzhijiao.system_frontend.service.template.SystemTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class HomeworkService extends ServiceImpl<HomeworkMapper, Homework> {

    @Autowired
    CopybookService copybookService;
    @Autowired
    TeacherHomeworkMapper teacherHomeworkMapper;
    @Autowired
    HomeworkMapper homeworkMapper;
    @Autowired
    FontService fontService;
    @Autowired
    HomeworkSubmissionService homeworkSubmissionService;
    @Autowired
    KlassHomeworkService klassHomeworkService;
    @Autowired
    CustomTemplateService customTemplateService;
    @Autowired
    SystemTemplateService systemTemplateService;
    @Autowired
    CustomTemplateImageService customTemplateImageService;
    @Autowired
    SystemTemplateImageService systemTemplateImageService;
    @Autowired
    HomeworkImageService homeworkImageService;
    @Autowired
    TeacherHomeworkService teacherHomeworkService;
    @Autowired
    KlassNoteReceiveService klassNoteReceiveService;
    @Autowired
    StudentNoteReceiveService studentNoteReceiveService;
    @Autowired
    StudentService studentService;
    @Autowired
    TemplateWordService templateWordService;
    @Autowired
    PictureService pictureService;
    @Autowired
    NoteService noteService;
    @Autowired
    OutstandingHomeworkService outstandingHomeworkService;



    /*
    * 获取作业详情（班级）
    * */
    public KlassHomeworkDetailDTO getHomeworkDetail(Integer homeworkId, Integer currentPage, Integer pageSize) {
        //设置下基本信息
        Homework homework = getById(homeworkId);
        KlassHomeworkDetailDTO klassHomeworkDetailDTO = new KlassHomeworkDetailDTO();
        BeanUtil.copyProperties(homework,klassHomeworkDetailDTO);
        klassHomeworkDetailDTO.setFont(fontService.getNameById(homework.getFontId()));
        //根据发布作业对象设置该作业里的接受作业的对象列表
        if(klassHomeworkDetailDTO.getTarget().equals("个人")){
            IPage<StudentDTO> page = new Page<>(currentPage,pageSize);
            klassHomeworkDetailDTO.setStudentList(homeworkSubmissionService.getStudentPage(homeworkId,page));
        }else if(klassHomeworkDetailDTO.getTarget().equals("集体")){
            IPage<Klass> page = new Page<>(currentPage,pageSize);
            klassHomeworkDetailDTO.setClassList(klassHomeworkService.getKlassByHomework(page,homeworkId));
        }else {
            throw new ServiceException(Constants.CODE_500,"系统错误，检测到错误的目标群体");
        }
        return klassHomeworkDetailDTO;
    }

    /*
    * 根据模板生成作业，需要给作业赋值模板属性，需要把图片复制过来，需要给模板使用次数加一
    * */
    @Transactional
    public void addByTemplate(Integer teacherId, PublishByTemplateDTO publishByTemplateDTO) {
        //从封装的类中取出数据
        String templateType = publishByTemplateDTO.getTemplateType();
        Integer templateId = publishByTemplateDTO.getTemplateId();
        String name = publishByTemplateDTO.getDescription().getName();
        String target = publishByTemplateDTO.getDescription().getTarget();
        String require = publishByTemplateDTO.getDescription().getRequire();
        String deadline = publishByTemplateDTO.getDescription().getDeadline();
        List<Integer> list = publishByTemplateDTO.getList();
        List<String> images;
        //先根据基础数据把生成作业的基础数据赋值。
        Homework homework = new Homework();
        Integer homeworkId;
        homework.setDeadline(deadline);
        homework.setTarget(target);
        homework.setRequirements(require);
        homework.setName(name);
        //接下来根据对应的模板进行赋值
        if (templateType.equals("自定义")){
            //先给作业赋值模板数据并生成
            homeworkId=addByCustom(templateId,homework);
            images = customTemplateImageService.getImages(templateId);
        } else if (templateType.equals("系统")) {
            //先给作业赋值模板数据并生成
            homeworkId=addBySystem(templateId,homework);
            images = systemTemplateImageService.getImages(templateId);
        }else {
            throw new ServiceException(Constants.CODE_500,"系统错误，提供了错误的模板类型！");
        }
        //把模板里的图片 内容复制过来
        homeworkImageService.addBatch(homeworkId,images);
        //把发布作业封装起来
        publishHomework(teacherId,homework,list,templateId,templateType);
    }

    /*
    * 根据自定义模板创建作业并添加,并返回作业id用于处理关系
    * 类设计错了，导致不好传参使得生成作业出现三个重复的即addHomework，addBySystem，addByCustom
    * */
    public Integer addByCustom(Integer templateId,Homework homework){
        CustomTemplate customTemplate = customTemplateService.getById(templateId);
        //先复制数据
        homework.setDifficulty(customTemplate.getDifficulty());
        homework.setType(customTemplate.getType());
        homework.setDetailType(customTemplate.getDetailType());
        homework.setWordCount(customTemplate.getWordCount());
        homework.setFontId(customTemplate.getFontId());
        //插入数据
        save(homework);
        //模板使用次数+1并更新
        customTemplate.setUsageFrequency(customTemplate.getUsageFrequency()+1);
        customTemplateService.updateById(customTemplate);
        return homework.getId();
    }

    /*
     * 根据系统模板创建作业并添加,并返回作业id用于处理关系
     * 类设计错了，导致不好传参使得生成作业出现三个重复的即addHomework，addBySystem，addByCustom
     * */
    public Integer addBySystem(Integer templateId,Homework homework){
        SystemTemplate systemTemplate = systemTemplateService.getById(templateId);
        //先复制数据
        homework.setDifficulty(systemTemplate.getDifficulty());
        homework.setType(systemTemplate.getType());
        homework.setDetailType(systemTemplate.getDetailType());
        homework.setWordCount(systemTemplate.getWordCount());
        homework.setFontId(systemTemplate.getFontId());
        //插入数据
        save(homework);
        return homework.getId();
    }

    /*
    * 创建字帖作业
    * */
    @Transactional
    public void publishCoopybook(HomeworkDTO homeworkDTO,Integer teacherId){
        //获得字帖
        Copybook copybook = copybookService.getById(homeworkDTO.getCopybookId());
        //保存作业
        Homework homework = addHomework(homeworkDTO,"字帖");
        //保存图片
        List<String> images = new ArrayList<>();
        images.add(copybook.getContent());
        homeworkImageService.addBatch(homework.getId(),images);
        //发布作业
        publishHomework(teacherId,homework,homeworkDTO.getList(),null,null);
    }

    /*
    * 创建专项作业
    * */
    @Transactional
    public void publishEarMarked(HomeworkDTO homeworkDTO,Integer teacherId) {
        //先把作业基本信息保存
        Homework homework = addHomework(homeworkDTO,"专项");
        //获取图片并开始拼接创建
        List<String> imagePaths = templateWordService.selectFilePathBatch(homeworkDTO.getWordId());
        List<String> homeworkUrls = pictureService.gatherImagesOfSpecial(imagePaths,"作业");
        //接着保存图片
        homeworkImageService.addBatch(homework.getId(),homeworkUrls);
        //接下来发布作业
        publishHomework(teacherId,homework,homeworkDTO.getList(),null,null);
    }

    /*
    * 创建综合作业
    * */
    @Transactional
    public void publishComprehensive(HomeworkDTO homeworkDTO,List<BufferedImage> imageList,Integer teacherId){
        //先把作业基本信息保存
        Homework homework = addHomework(homeworkDTO,"综合");
        List<String> urlList = imageList.stream()
                .map(image -> pictureService.saveFile(image, "作业"))
                .collect(Collectors.toList());
        //批量保存图片
        homeworkImageService.addBatch(homework.getId(),urlList);
        //接下来发布作业
        publishHomework(teacherId,homework,homeworkDTO.getList(),null,null);
    }





    /*
    * 统一的生成作业
    * 类设计错了，导致不好传参使得生成作业出现三个重复的即addHomework，addBySystem，addByCustom
    * */
    public Homework addHomework(HomeworkDTO homeworkDTO,String firstType){
        Homework homework = new Homework();
        BeanUtil.copyProperties(homeworkDTO.getDescription(),homework);
        homework.setType(firstType);
        homework.setRequirements(homeworkDTO.getDescription().getRequire());
        //只有专项有第二个类型
        if(firstType.equals("专项"))
            homework.setDetailType(homeworkDTO.getDescription().getType());
        if(firstType.equals("综合"))
            homework.setWordCount(homeworkDTO.getWordCount());
        else{
            if(ObjectUtil.isNull(homeworkDTO.getWordId())){
                homework.setWordCount(0);
            }else
                homework.setWordCount(homeworkDTO.getWordId().size());
        }
        save(homework);
        return homework;
    }

    /*
     * 不论是否根据模板生成的，最后都用此来处理发布作业，需要为所有学生新建作业
     * 需要给所有的学生发消息，需要给教师和作业之间的关系表增加联系
     * */
    @Transactional
    public void publishHomework(Integer teacherId,Homework homework,List<Integer> list,Integer templateId,String templateType){
        Integer homeworkId = homework.getId();
        String target = homework.getTarget();
        //给相应的学生发送消息
        if (target.equals("个人")){
            studentNoteReceiveService.addHomework(teacherId,list,homework);
            //批量创建作业作品，让学生们准备去完成
            homeworkSubmissionService.addByHomework(homeworkId,list);
        } else if (target.equals("集体")) {
            List<Integer> studentList = studentService.getByKlassList(list);
            System.out.println("下面为消息");
            klassNoteReceiveService.addHomework(teacherId,list,homework);
            //批量创建作业作品，让学生们准备去完成
            System.out.println("下面为作业作品");
            homeworkSubmissionService.addByHomework(homeworkId,studentList);
            System.out.println("下面为作业");
            klassHomeworkService.addBatch(homeworkId,list);
        }
        //增加教师作业联系
        teacherHomeworkService.addTeacherHomework(teacherId,homeworkId,templateId,templateType);
    }

    /*
    * 删除作业
    * */
    @Transactional
    public void deleteHomework(Integer homeworkId) {
        homeworkMapper.deleteById(homeworkId);
        homeworkImageService.deleteByHomework(homeworkId);
        klassHomeworkService.deleteByHomework(homeworkId);
        Integer noteId = noteService.deleteByHomework(homeworkId);
        klassNoteReceiveService.deleteByHomework(noteId);
        studentNoteReceiveService.deleteByHomework(noteId);
        teacherHomeworkService.deleteByHomework(homeworkId);
        homeworkSubmissionService.deleteByHomework(homeworkId);
    }
}
