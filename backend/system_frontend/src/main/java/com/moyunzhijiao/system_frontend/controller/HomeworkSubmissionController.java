package com.moyunzhijiao.system_frontend.controller;


import cn.hutool.core.bean.BeanUtil;
import com.moyunzhijiao.system_frontend.common.Result;
import com.moyunzhijiao.system_frontend.controller.dto.HomeworkSubmissionDetailDTO;
import com.moyunzhijiao.system_frontend.entity.homework.CharacterAnalysis;
import com.moyunzhijiao.system_frontend.entity.homework.Homework;
import com.moyunzhijiao.system_frontend.entity.homework.HomeworkSubmission;
import com.moyunzhijiao.system_frontend.service.FontService;
import com.moyunzhijiao.system_frontend.service.StudentService;
import com.moyunzhijiao.system_frontend.service.homework.CharacterAnalysisService;
import com.moyunzhijiao.system_frontend.service.homework.HomeworkImageService;
import com.moyunzhijiao.system_frontend.service.homework.HomeworkService;
import com.moyunzhijiao.system_frontend.service.homework.HomeworkSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeworkSubmissionController {

    @Autowired
    HomeworkSubmissionService homeworkSubmissionService;
    @Autowired
    HomeworkService homeworkService;
    @Autowired
    HomeworkImageService homeworkImageService;
    @Autowired
    CharacterAnalysisService characterAnalysisService;
    @Autowired
    FontService fontService;
    @Autowired
    StudentService studentService;
    /*
    * 教师获取一个学生的作业详情
    * */
    @GetMapping("/ciep/stu-homework-detail")
    public Result submissionDetail(@RequestParam Integer id){
        return findSubmission(id);
    }

    @GetMapping("/cieps/homework-detail")
    public Result submissionDetail_(@RequestParam(required = false) Integer stuNo,
                                    @RequestParam Integer homeworkId){
        Integer stuId=studentService.getStuIdByStuNo(stuNo);
        Integer id=homeworkSubmissionService.getSubmissionByStuAndWork(stuId,homeworkId).getId();
        return findSubmission(id);
    }

    public Result findSubmission(Integer id){
        HomeworkSubmission homeworkSubmission = homeworkSubmissionService.getById(id);
        Integer homeworkId = homeworkSubmission.getHomeworkId();
        HomeworkSubmissionDetailDTO homeworkSubmissionDetailDTO = new HomeworkSubmissionDetailDTO();
        //先赋值作业的内容
        Homework homework = homeworkService.getById(homeworkId);
        BeanUtil.copyProperties(homework,homeworkSubmissionDetailDTO);
        homeworkSubmissionDetailDTO.setFont(fontService.getNameById(homework.getFontId()));
        //赋值作业的图片列表
        homeworkSubmissionDetailDTO.setImgList(homeworkImageService.getUrlBatch(homeworkId));
        //再赋值作业作品的数据
        BeanUtil.copyProperties(homeworkSubmission,homeworkSubmissionDetailDTO);
        Integer homeworkSubmissionId = homeworkSubmission.getId();
        //最后赋值里面的详细内容例如分析内容
        homeworkSubmissionDetailDTO.setCharacterAnalysisList(characterAnalysisService.getBySubmission(homeworkSubmissionId));
        return Result.success(homeworkSubmissionDetailDTO);
    }
}
