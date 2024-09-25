package com.moyunzhijiao.system_backend.controller.analysis;

import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.controller.dto.AnalysisDTO;
import com.moyunzhijiao.system_backend.controller.dto.base.RegionDTO;
import com.moyunzhijiao.system_backend.entiy.resource.Article;
import com.moyunzhijiao.system_backend.entiy.resource.Video;
import com.moyunzhijiao.system_backend.service.AnalysisService;
import com.moyunzhijiao.system_backend.service.base.RegionService;
import com.moyunzhijiao.system_backend.service.base.SchoolService;
import com.moyunzhijiao.system_backend.service.front.StudentService;
import com.moyunzhijiao.system_backend.service.front.TeacherService;
import com.moyunzhijiao.system_backend.service.resource.ArticleService;
import com.moyunzhijiao.system_backend.service.resource.VideoService;
import com.moyunzhijiao.system_backend.service.word.SystemTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/backend/analysis")
public class DataAnalysisController {
    @Autowired
    SchoolService schoolService;
    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    RegionService regionService;
    @Autowired
    AnalysisService analysisService;
    @Autowired
    SystemTemplateService systemTemplateService;
    @Autowired
    ArticleService articleService;
    @Autowired
    VideoService videoService;

    /*
    * 获取关于新增的数据，如本日学生新增量，本日教师新增量，本月学生新增量，本月教师新增量
    * */
    @PreAuthorize("hasAuthority('用户数据分析')")
    @GetMapping("/add-data")
    public Result getAddData(){
        Map<String,Integer> addData = new HashMap<>();
        addData.put("dayStudent",analysisService.getSingleAddData("day","student"));
        addData.put("dayTeacher",analysisService.getSingleAddData("day","teacher"));
        addData.put("daySchool",analysisService.getSingleAddData("day","school"));
        addData.put("monthStudent",analysisService.getSingleAddData("month","student"));
        addData.put("monthTeacher",analysisService.getSingleAddData("month","teacher"));
        addData.put("monthSchool",analysisService.getSingleAddData("month","school"));
        return Result.success(addData);
    }

    /*
    * 获取每个省份地区的数据
    * */
    @PreAuthorize("hasAuthority('用户数据分析')")
    @GetMapping("/region-data")
    public Result getRegionData(){
        List<RegionDTO> list = analysisService.getProvinceStatistics();
        return Result.success(list);
    }

    /*
     * 获取关于占比的数据
     * */
    @PreAuthorize("hasAuthority('字库分析')")
    @GetMapping("/radio-data")
    public Result getRadioData(){
        Map<String,Integer> map = systemTemplateService.getRadioData();
        return Result.success(map);
    }

    /*
     * 获取每个系统模板关于字体以及年级的数据
     * */
    @PreAuthorize("hasAuthority('字库分析')")
    @GetMapping("/font-grade-data")
    public Result getFontData(){
        Map<String,List<?>> result = analysisService.getFontAndGradeData();
        return Result.success(result);
    }


    /*
     * 获取每个省份地区的数据
     * */
    @PreAuthorize("hasAuthority('书法知识资源分析')")
    @GetMapping("/article-data")
    public Result getArticleData(){
        List<AnalysisDTO> list = analysisService.getTypeCount("article");
        return Result.success(list);
    }

    /*
     * 获取每个省份地区的数据
     * */
    @PreAuthorize("hasAuthority('书法知识资源分析')")
    @GetMapping("/video-data")
    public Result getVideoData(){
        List<AnalysisDTO> list = analysisService.getTypeCount("video");
        return Result.success(list);
    }
}
