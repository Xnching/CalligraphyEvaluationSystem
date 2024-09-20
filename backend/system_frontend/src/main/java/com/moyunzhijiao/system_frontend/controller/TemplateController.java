package com.moyunzhijiao.system_frontend.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyunzhijiao.system_frontend.common.Constants;
import com.moyunzhijiao.system_frontend.common.Result;
import com.moyunzhijiao.system_frontend.controller.dto.TeacherTemplateDTO;
import com.moyunzhijiao.system_frontend.entity.Copybook;
import com.moyunzhijiao.system_frontend.entity.homework.Homework;
import com.moyunzhijiao.system_frontend.entity.template.CustomTemplate;
import com.moyunzhijiao.system_frontend.entity.template.SystemTemplate;
import com.moyunzhijiao.system_frontend.service.CopybookService;
import com.moyunzhijiao.system_frontend.service.collection.TeaWorksCollectionService;
import com.moyunzhijiao.system_frontend.service.homework.TeacherHomeworkService;
import com.moyunzhijiao.system_frontend.service.template.CustomTemplateService;
import com.moyunzhijiao.system_frontend.service.template.SystemTemplateService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TemplateController {
    @Autowired
    SystemTemplateService systemTemplateService;
    @Autowired
    CustomTemplateService customTemplateService;
    @Autowired
    CopybookService copybookService;
    @Autowired
    TeacherHomeworkService teacherHomeworkService;
    @Autowired
    TeaWorksCollectionService teaWorksCollectionService;


    @Operation(summary = "获取一个教师的已保存的模板，即自定义模板和所有的系统模板")
    @GetMapping("/ciep/added-template-list")
    public Result addedTemplateOfTeacher(@RequestHeader("authorization") String token
                , @RequestParam Integer type, @RequestParam Integer currentPage, @RequestParam Integer pageSize){
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer teacherId = Integer.valueOf(jwt.getAudience().get(0));
        TeacherTemplateDTO teacherTemplateDTO = new TeacherTemplateDTO();
        System.out.println("看看:"+type);
        switch (type){
            //就一个分页查询内容，没法全要
            case 0:
                return Result.error(Constants.CODE_400,"一个分页查询数据不准要多个分页数据！");
//                teacherTemplateDTO.setSystem(systemTemplateService.getByTeacher(teacherId,currentPage,pageSize));
//                teacherTemplateDTO.setSelf(customTemplateService.getByTeacher(teacherId,currentPage,pageSize));
//                break;
            case 1:     //获取系统模板
                teacherTemplateDTO.setSystem(systemTemplateService.getByTeacher(teacherId,currentPage,pageSize));
                break;
            case 2:     //获取教师保存的自定义模板
                teacherTemplateDTO.setSelf(customTemplateService.getByTeacher(teacherId,currentPage,pageSize));
                break;
            default:
                return Result.error(Constants.CODE_401,"type错误！");
        }
        return Result.success(teacherTemplateDTO);
    }


    @Operation(summary = "查找三个列表用于添加模板，即系统字帖列表，已有作业列表，收藏的作品列表")
    @GetMapping("/ciep/unadded-template-list")
    public Result findThingToAddTemplate(@RequestHeader("authorization") String token
            , @RequestParam Integer type, @RequestParam Integer currentPage, @RequestParam Integer pageSize){
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer teacherId = Integer.valueOf(jwt.getAudience().get(0));
        switch (type){
            case 0:
                return Result.error(Constants.CODE_400,"一个分页查询数据不准要多个分页数据！");
            case 1:     //获取系统字帖
                IPage<Copybook> copybookIPage = new Page<>(currentPage,pageSize);
                copybookIPage = copybookService.getPageAndFont(copybookIPage);
                return Result.success(copybookIPage);
            case 2:     //获取该教师已有作业
                IPage<Homework> homeworkIPage = new Page<>(currentPage,pageSize);
                homeworkIPage = teacherHomeworkService.getAllHomeworkPageOfTeacher(homeworkIPage,teacherId);
                return Result.success(homeworkIPage);
            case 3:     //获取该教师所收藏作品包括作业作品，优秀竞赛作品，优秀作业作品
                IPage<Homework> collectionPage = new Page<>(currentPage,pageSize);
                collectionPage = teaWorksCollectionService.getHomeworkOfTeacher(collectionPage,teacherId);
                return Result.success(collectionPage);
            default:
                return Result.error(Constants.CODE_401,"type错误！");
        }
    }

    @Operation(summary = "一个模板的详细信息")
    @GetMapping("/ciep/template-detail")
    public Result findTemplateDetail( @RequestParam Integer type, @RequestParam Integer templateId){
        switch (type){
            case 1:     //系统模板的
                SystemTemplate systemTemplate;
                systemTemplate = systemTemplateService.getDetailByTeacherSingle(templateId);
                return Result.success(systemTemplate);
            case 2:     //自定义模板的
                CustomTemplate customTemplate;
                customTemplate = customTemplateService.getDeatilByTeacherSingle(templateId);
                return Result.success(customTemplate);
            default:
                return Result.error(Constants.CODE_401,"type错误！");
        }
    }

    /*
    * 获取系统字帖列表
    * */
    @Operation(summary = "获取系统字帖列表")
    @GetMapping("/ciep/copybook-list")
    public Result findCopybookList( @RequestParam Integer currentPage, @RequestParam Integer pageSize){
        IPage<Copybook> copybookIPage = new Page<>(currentPage,pageSize);
        copybookIPage = copybookService.page(copybookIPage);
        return Result.success(copybookIPage);
    }

    /*
    * 删除一个教师的自定义模板
    * */
    @Operation(summary = "删除一个教师的自定义模板")
    @PostMapping("/ciep/template")
    public Result deleteTemplateOfTeacher( @RequestParam Integer templateId){
        customTemplateService.deleteTemplateOfTeacher(templateId);
        return Result.success();
    }
}
