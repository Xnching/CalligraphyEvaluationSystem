package com.moyunzhijiao.system_frontend.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_frontend.common.Constants;
import com.moyunzhijiao.system_frontend.common.Result;
import com.moyunzhijiao.system_frontend.controller.dto.KlassDTO;
import com.moyunzhijiao.system_frontend.controller.dto.KlassDetailDTO;
import com.moyunzhijiao.system_frontend.controller.dto.StuInKlassDTO;
import com.moyunzhijiao.system_frontend.service.HomeworkSubmissionService;
import com.moyunzhijiao.system_frontend.service.KlassService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ciep")
public class KlassController {

    @Autowired
    KlassService klassService;
    @Autowired
    HomeworkSubmissionService homeworkSubmissionService;

    @Operation(summary = "查找已添加的班级和未添加的班级")
    @Parameters({
            @Parameter(name = "type", description = "类型", required = true, in = ParameterIn.QUERY),
            @Parameter(name = "currentPage", description = "当前页码", required = true, in = ParameterIn.QUERY),
            @Parameter(name = "pageSize", description = "每页显示的记录数", required = true, in = ParameterIn.QUERY),
            @Parameter(name = "year", description = "年份", required = false, in = ParameterIn.QUERY),
            @Parameter(name = "grade", description = "年级", required = false, in = ParameterIn.QUERY),
            @Parameter(name = "klass", description = "班级", required = false, in = ParameterIn.QUERY)
    })
    @GetMapping("/class")
    public Result findPageByTeacher(@RequestHeader("authorization") String token,@RequestParam Integer type, @RequestParam Integer currentPage, @RequestParam Integer pageSize, @RequestParam Integer schoolId,
                                    @RequestParam(defaultValue = "")String year, @RequestParam(defaultValue = "")String grade, @RequestParam(defaultValue = "")String klass){
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer teacherId = Integer.valueOf(jwt.getAudience().get(0));
        switch (type){
            case 1, 0:
                IPage<KlassDTO> page = klassService.getKlassByTeacher(type,currentPage,pageSize,teacherId,schoolId,grade,klass,year);
                return Result.success(page);
            default:
                return Result.error(Constants.CODE_401,"type内容错误！");
        }
    }

    @Operation(summary = "让教师不教授该班级，删除班级的教师id，逻辑删除，把delete_flag置为1")
    @PutMapping("/class")
    public Result deleteKlass(@RequestBody Map<String, String> params){
        Integer klassId = Integer.valueOf(params.get("classId"));
        klassService.deleteKlassOfTeacher(klassId);
        return Result.success();
    }

    @Operation(summary = "给教师添加班级")
    @PutMapping("/add-class")
    public Result addKlass(@RequestHeader("authorization") String token,@RequestBody Map<String, String> params){
        Integer klassId = Integer.valueOf(params.get("classId"));
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer teacherId = Integer.valueOf(jwt.getAudience().get(0));
        klassService.addKlassOfTeacher(teacherId,klassId);
        return Result.success();
    }


    @Operation(summary = "班级详情列表，里面有已截止作业列表以及学生列表")
    @GetMapping("/class-detail")
    public Result klassDetail(@RequestParam Integer classId,
                              @RequestParam Integer currentPage, @RequestParam Integer pageSize){
        KlassDetailDTO klassDetailDTO = klassService.getKlassDetaiList(classId,currentPage,pageSize);
        return Result.success(klassDetailDTO);
    }

    @Operation(summary = "班级里的学生详情,0已提交未提交;1已提交;2未提交")
    @GetMapping("/stu-detail")
    public Result stuDeatailInKlass(@RequestParam("stuId")Integer stuId,@RequestParam("currentPage")Integer currentPage
            ,@RequestParam("pageSize")Integer pageSize,@RequestParam("type")Integer type){
        StuInKlassDTO stuInKlassDTO = new StuInKlassDTO();
        switch (type){
            case 0:
                stuInKlassDTO.setSubmitedHomeworkList(homeworkSubmissionService.getSubmitedPage(stuId,currentPage,pageSize));
                stuInKlassDTO.setUnSubmitedHomeworkList(homeworkSubmissionService.getUnSubmitedPage(stuId,currentPage,pageSize));
                break;
            case 1:
                stuInKlassDTO.setSubmitedHomeworkList(homeworkSubmissionService.getSubmitedPage(stuId,currentPage,pageSize));
                break;
            case 2:
                stuInKlassDTO.setUnSubmitedHomeworkList(homeworkSubmissionService.getUnSubmitedPage(stuId,currentPage,pageSize));
                break;
            default:
                return Result.error(Constants.CODE_401,"type内容错误！");
        }
        return Result.success(stuInKlassDTO);
    }

}
