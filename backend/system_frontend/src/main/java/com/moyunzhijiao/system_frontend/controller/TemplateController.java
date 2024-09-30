package com.moyunzhijiao.system_frontend.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyunzhijiao.system_frontend.common.Constants;
import com.moyunzhijiao.system_frontend.common.Result;
import com.moyunzhijiao.system_frontend.controller.dto.TeacherTemplateDTO;
import com.moyunzhijiao.system_frontend.controller.dto.TemplateDTO;
import com.moyunzhijiao.system_frontend.entity.Copybook;
import com.moyunzhijiao.system_frontend.entity.TemplateWord;
import com.moyunzhijiao.system_frontend.entity.homework.Homework;
import com.moyunzhijiao.system_frontend.entity.template.CustomTemplate;
import com.moyunzhijiao.system_frontend.entity.template.SystemTemplate;
import com.moyunzhijiao.system_frontend.exception.ServiceException;
import com.moyunzhijiao.system_frontend.service.CopybookService;
import com.moyunzhijiao.system_frontend.service.PictureService;
import com.moyunzhijiao.system_frontend.service.collection.TeaWorksCollectionService;
import com.moyunzhijiao.system_frontend.service.homework.HomeworkImageService;
import com.moyunzhijiao.system_frontend.service.homework.HomeworkService;
import com.moyunzhijiao.system_frontend.service.homework.TeacherHomeworkService;
import com.moyunzhijiao.system_frontend.service.template.CustomTemplateService;
import com.moyunzhijiao.system_frontend.service.template.SystemTemplateService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Autowired
    PictureService pictureService;
    @Autowired
    HomeworkService homeworkService;
    @Autowired
    HomeworkImageService homeworkImageService;


    @Operation(summary = "获取一个教师的已保存的模板，即自定义模板和所有的系统模板")
    @GetMapping("/ciep/added-template-list")
    public Result addedTemplateOfTeacher(@RequestHeader("authorization") String token
                , @RequestParam Integer type, @RequestParam Integer currentPage, @RequestParam Integer pageSize){
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer teacherId = Integer.valueOf(jwt.getAudience().get(0));
        String userType = jwt.getClaim("userType").asString();
        TeacherTemplateDTO teacherTemplateDTO = new TeacherTemplateDTO();
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
                teacherTemplateDTO.setSelf(customTemplateService.getByType(teacherId,userType,currentPage,pageSize));
                break;
            default:
                return Result.error(Constants.CODE_401,"type错误！");
        }
        return Result.success(teacherTemplateDTO);
    }


    @Operation(summary = "查找三个列表用于添加模板，即系统字帖列表，已有作业列表，收藏的作品列表")
    @GetMapping("/ciep/unadded-template-list")
    public Result findThingToAddTemplate(@RequestHeader("authorization") String token
            , @RequestParam Integer currentPage, @RequestParam Integer pageSize){
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer teacherId = Integer.valueOf(jwt.getAudience().get(0));
        //获取该教师已有作业
        IPage<Homework> homeworkIPage = new Page<>(currentPage, pageSize);
        homeworkIPage = teacherHomeworkService.getAllHomeworkPageOfTeacher(homeworkIPage, teacherId);
        return Result.success(homeworkIPage);
    }

    @Operation(summary = "教师将一个作业创建为模板")
    @PostMapping("/ciep/add-template")
    public Result addHomeworkAsTemplate(@RequestHeader("authorization") String token, @RequestBody Map<String,Object> params){
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer teacherId = Integer.valueOf(jwt.getAudience().get(0));
        Integer homeworkId = (Integer) params.get("homeworkId");
        String name = (String) params.get("name");
        Homework homework = homeworkService.getById(homeworkId);
        List<String> urls = homeworkImageService.getUrlBatch(homeworkId);
        customTemplateService.addHomeworkAsTemplate(teacherId,name,homework,urls);
        return Result.success();
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
                customTemplate = customTemplateService.getDeatilBySingle(templateId);
                return Result.success(customTemplate);
            default:
                return Result.error(Constants.CODE_401,"type错误！");
        }
    }

    @Operation(summary = "获取系统字帖列表")
    @GetMapping("/ciep/copybook-list")
    public Result findCopybookList( @RequestParam Integer currentPage, @RequestParam Integer pageSize){
        IPage<Copybook> copybookIPage = new Page<>(currentPage,pageSize);
        copybookIPage = copybookService.page(copybookIPage);
        return Result.success(copybookIPage);
    }

    @Operation(summary = "删除一个自定义模板")
    @PostMapping("/ciep/template")
    public Result deleteTemplateOfTeacher( @RequestParam Integer templateId){
        customTemplateService.deleteTemplate(templateId);
        return Result.success();
    }

    @Operation(summary = "创建一个专项模板")
    @PostMapping("/ciep/earmarked")
    public Result addSpicialTemplate(@RequestHeader("authorization") String token,@RequestBody TemplateDTO templateDTO){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        String userType = JWT.decode(token).getClaim("userType").asString();
        customTemplateService.addSpecialTemplate(templateDTO,userId,userType);
        return Result.success();
    }

    @Operation(summary = "获取综合系统模板的在线预览")
    @PostMapping("/ciep/preview")
    public Result getPicture(@RequestBody Map<String,Object> params){
        String composing = (String) params.get("composing");
        String textContent = (String) params.get("textContent");
        Integer fontId = (Integer) params.get("fontId");
        if(fontId==null){
            throw new ServiceException(Constants.CODE_400,"未选择字体！");
        }
        if(StrUtil.isEmpty(composing)){
            throw new ServiceException(Constants.CODE_400,"未选择版式！");
        }
        //获取用于给前端展示的三维数组模板字的id
        List<List<List<TemplateWord>>> list = pictureService.getPictureOfComprehensive(textContent,fontId,composing);
        List<List<List<Integer>>> idList = pictureService.getIdArray(list);
        Integer wordCount = customTemplateService.countIntegers(idList);
        //根据id拼图片
        List<BufferedImage> imageList = pictureService.gatherImagesOfComprehensive(idList,composing);
        //把图片准备好发给前端
        List<String> resources = pictureService.exchangeImageToBase64(imageList);
        Map<String,Object> result = new HashMap<>();
        result.put("resource",resources);
        result.put("wordCount",wordCount);
        return Result.success(result);
    }

    @Operation(summary = "创建一个字帖模板")
    @PostMapping("/ciep/copybook")
    public Result addCopybookTemplate(@RequestHeader("authorization") String token,@RequestBody TemplateDTO templateDTO){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        String userType = JWT.decode(token).getClaim("userType").asString();
        customTemplateService.addCopybookTemplate(templateDTO,userId,userType);
        return Result.success();
    }

    @Operation(summary = "创建一个综合模板")
    @PostMapping("/ciep/comprehensive")
    public Result addComprehensiveTemplate(@RequestHeader("authorization") String token, @RequestBody TemplateDTO templateDTO){
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        String userType = JWT.decode(token).getClaim("userType").asString();
        List<BufferedImage> imageList = pictureService.exchangeBase64ToImage(templateDTO.getContentList());
        System.out.println("让我看下"+templateDTO);
        //保存图片
        customTemplateService.addComprehensiveTemplate(imageList,templateDTO,userId,userType);
        return Result.success();
    }
}
