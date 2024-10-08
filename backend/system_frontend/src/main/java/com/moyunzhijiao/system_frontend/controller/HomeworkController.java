package com.moyunzhijiao.system_frontend.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyunzhijiao.system_frontend.common.Constants;
import com.moyunzhijiao.system_frontend.common.Result;
import com.moyunzhijiao.system_frontend.controller.dto.homework.HomeworkDTO;
import com.moyunzhijiao.system_frontend.controller.dto.KlassHomeworkDetailDTO;
import com.moyunzhijiao.system_frontend.controller.dto.PublishByTemplateDTO;
import com.moyunzhijiao.system_frontend.entity.Grade;
import com.moyunzhijiao.system_frontend.entity.Klass;
import com.moyunzhijiao.system_frontend.entity.homework.Homework;
import com.moyunzhijiao.system_frontend.exception.ServiceException;
import com.moyunzhijiao.system_frontend.service.GradeService;
import com.moyunzhijiao.system_frontend.service.homework.HomeworkImageService;
import com.moyunzhijiao.system_frontend.service.homework.HomeworkService;
import com.moyunzhijiao.system_frontend.service.homework.KlassHomeworkService;
import com.moyunzhijiao.system_frontend.service.homework.TeacherHomeworkService;
import com.moyunzhijiao.system_frontend.service.note.KlassNoteReceiveService;
import com.moyunzhijiao.system_frontend.service.note.StudentNoteReceiveService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

@RestController
public class HomeworkController {
    @Autowired
    HomeworkService homeworkService;
    @Autowired
    TeacherHomeworkService teacherHomeworkService;
    @Autowired
    KlassNoteReceiveService klassNoteReceiveService;
    @Autowired
    StudentNoteReceiveService studentNoteReceiveService;
    @Autowired
    KlassHomeworkService klassHomeworkService;
    @Autowired
    HomeworkImageService homeworkImageService;
    @Autowired
    GradeService gradeService;

    @Operation(summary = "教师的查询作业列表，六个分页查询")
    @GetMapping("/ciep/homework")
    public Result findHomeworkPageOfTeacher(@RequestHeader("authorization") String token
            , @RequestParam Integer type, @RequestParam Integer currentPage, @RequestParam Integer pageSize
            ,@RequestParam(defaultValue = "")String name){
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer teacherId = Integer.valueOf(jwt.getAudience().get(0));
        IPage<HomeworkDTO> page = new Page<>(currentPage,pageSize);
        page = teacherHomeworkService.getHomeworkPageOfTeacher(page,type,teacherId,name);
        return Result.success(page);
    }

    @Operation(summary = "延迟作业")
    @PostMapping("/ciep/postpone")
    public Result postponeHomework(@RequestBody Map<String, String> request) {
        String homeworkId = request.get("homeworkId");
        String newDate = request.get("newDate");
        Homework homework ;
        homework = homeworkService.getById(homeworkId);
        //字符串能直接修改数据库的时间数据类型
        homework.setDeadline(newDate);
        homeworkService.updateById(homework);
        return Result.success();
    }

    @Operation(summary = "催促作业，-1表示没有,type中0表示集体作业催促，1表示个人作业催促")
    @PostMapping("/ciep/urge")
    public Result urgeHomework(@RequestHeader("authorization") String token,@RequestBody Map<String, String> request){
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer teacherId = Integer.valueOf(jwt.getAudience().get(0));
        Integer homeworkId = Integer.valueOf(request.get("homeworkId"));
        //检查作业
        if(homeworkId==-1){
            return Result.error(Constants.CODE_401,"未指定作业！");
        }
        int stuId = Integer.parseInt(request.get("stuId"));
        int type = Integer.parseInt(request.get("type"));
        try {
            if (stuId!=-1){
                studentNoteReceiveService.urgeHomeworkSingle(teacherId,stuId,homeworkService.getById(homeworkId));
            } else {
                switch (type){
                    case 0:
                        klassNoteReceiveService.urgeHomework(teacherId,homeworkService.getById(homeworkId));
                        break;
                    case 1:
                        studentNoteReceiveService.urgeHomeworkBatch(teacherId,homeworkService.getById(homeworkId));
                        break;
                    default:
                        return Result.error(Constants.CODE_401,"type内容错误！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(Constants.CODE_500,"系统错误，催促失败！");
        }
        return Result.success();
    }

    @Operation(summary = "获取作业详情（班级）")
    @GetMapping("/ciep/class-homework-detail")
    public Result detailInKlass(@RequestParam Integer homeworkId, @RequestParam Integer currentPage, @RequestParam Integer pageSize){
        KlassHomeworkDetailDTO klassHomeworkDetailDTO;
        klassHomeworkDetailDTO = homeworkService.getHomeworkDetail(homeworkId,currentPage,pageSize);
        return Result.success(klassHomeworkDetailDTO);
    }

    @Operation(summary = "教师根据模板发布作业")
    @PostMapping("/ciep/publish-homework")
    public Result publishHomeworkByTemplate(@RequestHeader("authorization") String token,@RequestBody PublishByTemplateDTO publishByTemplateDTO){
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户ID
        //从封装的类中取出数据
        Integer teacherId = Integer.valueOf(jwt.getAudience().get(0));
        //根据模板新增作业
        homeworkService.addByTemplate(teacherId,publishByTemplateDTO);
        return Result.success();
    }

    /*
    * 获取一个集体作业下的所有班级
    * */
    @GetMapping("/ciep/class-homework")
    public Result findKlassOfHomework(@RequestParam Integer homeworkId){
        IPage<Klass> page = new Page<>(1,Integer.MAX_VALUE);
        List<Grade> gradeList = new ArrayList<>();
        List<Klass> klassList = klassHomeworkService.getKlassByHomework(page,homeworkId).getRecords();
        // 遍历班级列表
        for (Klass klass : klassList) {
            // 查找或创建对应的年级
            Grade grade = findOrCreateGrade(gradeList, klass.getGradeId());
            // 将班级添加到年级的班级列表中
            grade.getList().add(klass);
        }
        return Result.success(gradeList);
    }
    // 查找或创建年级的方法
    private Grade findOrCreateGrade(List<Grade> gradeList, Integer gradeId) {
        for (Grade grade : gradeList) {
            if (grade.getId().equals(gradeId)) {
                return grade;
            }
        }
        Grade newGrade = new Grade();
        newGrade.setId(gradeId);
        newGrade.setName(gradeService.getNameById(gradeId));
        newGrade.setList(new ArrayList<>());
        gradeList.add(newGrade);
        return newGrade;
    }

    @Operation(summary = "创建专项作业")
    @PostMapping("/ciep/publish-earmarked")
    public Result publishEarMarked(@RequestHeader("authorization") String token,@RequestBody HomeworkDTO homeworkDTO){
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer teacherId = Integer.valueOf(jwt.getAudience().get(0));
        homeworkService.publishEarMarked(homeworkDTO,teacherId);
        return Result.success();
    }

    @Operation(summary = "创建字帖作业")
    @PostMapping("/ciep/publish-copybook")
    public Result publishCopybook(@RequestHeader("authorization") String token,@RequestBody HomeworkDTO homeworkDTO){
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer teacherId = Integer.valueOf(jwt.getAudience().get(0));
        homeworkService.publishCoopybook(homeworkDTO,teacherId);
        return Result.success();
    }

    @Operation(summary = "创建综合作业")
    @PostMapping("/ciep/publish-comprehensive")
    public Result publishComprehensive(@RequestHeader("authorization") String token,@RequestBody HomeworkDTO homeworkDTO){
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer teacherId = Integer.valueOf(jwt.getAudience().get(0));
        List<BufferedImage> imageList = homeworkDTO.getContentList().stream().map(content->{
            String temp = content.replaceFirst("^data:image/[^;]+;base64,", "");;
            BufferedImage image;
            byte[] imageBytes = Base64.getDecoder().decode(temp);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
            try {
                image = ImageIO.read(bis);
            } catch (IOException e) {
                e.printStackTrace();
                throw new ServiceException(Constants.CODE_500,"系统错误");
            }
            return image;
        }).toList();
        homeworkService.publishComprehensive(homeworkDTO,imageList,teacherId);
        return Result.success();
    }

    /*
    * 删除作业
    * */
    @DeleteMapping("/ciep/homework")
    public Result deleteHomework(@RequestBody Map<String,Integer> params){
        Integer homeworkId = params.get("homeworkId");
        homeworkService.deleteHomework(homeworkId);
        return Result.success();
    }

    @GetMapping("nnn")
    public Result getStudentPageOfKlassHomework(@RequestParam Integer homeworkId, @RequestParam Integer currentPage, @RequestParam Integer pageSize){
        return Result.success();
    }
}
