package com.moyunzhijiao.system_frontend.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_frontend.common.Result;
import com.moyunzhijiao.system_frontend.controller.dto.*;
import com.moyunzhijiao.system_frontend.service.PraticeService;
import com.moyunzhijiao.system_frontend.service.outstanding.OutstandingCompetitionService;
import com.moyunzhijiao.system_frontend.service.outstanding.OutstandingHomeworkService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "学生接口")
@RequestMapping("/cieps")
public class PraticeController {
    @Autowired
    PraticeService praticeService;

    @Autowired
    OutstandingCompetitionService outstandingCompetitionService;

    @Autowired
    OutstandingHomeworkService outstandingHomeworkService;

    @GetMapping("/school-pratice")
    public Result getSchoolPratice(@RequestHeader("authorization") String token,
                                   @RequestParam Integer currentPage,
                                   @RequestParam Integer pageSize,
                                   @RequestParam Integer type){
        DecodedJWT jwt = JWT.decode(token);
        Integer stuId = Integer.valueOf(jwt.getAudience().get(0));
        if (type==1){
            IPage<HomeworkDTO> page=praticeService.getSchoolPratice(stuId,currentPage,pageSize);
            return Result.success(page);
        }else{
            IPage<HomeworkDTO> page=praticeService.getSchoolPratice_(stuId,currentPage,pageSize);
            return Result.success(page);
        }
    }

    @GetMapping("/self-pratice")
    public Result getSelfPratice(@RequestHeader("authorization") String token,
                                   @RequestParam Integer currentPage,
                                   @RequestParam Integer pageSize,
                                   @RequestParam Integer type){
        DecodedJWT jwt = JWT.decode(token);
        Integer stuId = Integer.valueOf(jwt.getAudience().get(0));
        if (type==1){
            IPage<PracticeDTO> page=praticeService.getSelfPratice(stuId,currentPage,pageSize);
            return Result.success(page);
        }else{
            IPage<PracticeDTO> page=praticeService.getSelfPratice_(stuId,currentPage,pageSize);
            return Result.success(page);
        }
    }

    @GetMapping("/outstanding")
    public Result getOutstanding(@RequestHeader("authorization") String token,
                                 @RequestParam Integer currentPage,
                                 @RequestParam Integer pageSize,
                                 @RequestParam Integer type){
        DecodedJWT jwt = JWT.decode(token);
        if (type==1){
            IPage<OutstandingCompetitionDTO> page=outstandingCompetitionService.getList(currentPage,pageSize);
            return Result.success(page);
        }else{
            IPage<OutstandingHomeworkDTO> page=outstandingHomeworkService.getList(currentPage,pageSize);
            return Result.success(page);
        }
    }
}
