package com.moyunzhijiao.system_backend.controller.outstanding;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.controller.dto.homework.HomeworkSubmissionDTO;
import com.moyunzhijiao.system_backend.controller.dto.outstanding.UnreviewedOutstandingDTO;
import com.moyunzhijiao.system_backend.entiy.outstanding.OutstandingHomework;
import com.moyunzhijiao.system_backend.entiy.outstanding.UnreviewedOutstanding;
import com.moyunzhijiao.system_backend.service.homework.HomeworkSubmissionService;
import com.moyunzhijiao.system_backend.service.outstanding.OutstandingHomeworkService;
import com.moyunzhijiao.system_backend.service.outstanding.UnreviewedOutstandingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/backend/review-outstanding")
public class ReviewController {
    @Autowired
    UnreviewedOutstandingService unreviewedOutstandingService;
    @Autowired
    HomeworkSubmissionService homeworkSubmissionService;
    @Autowired
    OutstandingHomeworkService outstandingHomeworkService;

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "")String str){
        IPage<UnreviewedOutstandingDTO> page = new Page<>(pageNum,pageSize);
        page = unreviewedOutstandingService.getPage(page,str);
        return Result.success(page);
    }

    @GetMapping("/detail")
    public Result getDetail(@RequestParam Integer submissionId){
        HomeworkSubmissionDTO homeworkSubmissionDTO = homeworkSubmissionService.getDetail(submissionId);
        return Result.success(homeworkSubmissionDTO);
    }
    /*
    * 只能用body接收
    * */
    @PostMapping("/review")
    public Result reviewOutstanding(@RequestHeader("token") String token, @RequestBody Map<String,String> r){
        Integer submissionId = Integer.valueOf(r.get("submissionId"));
        String result = r.get("result");
        //解码token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        UnreviewedOutstanding unreviewedOutstanding = unreviewedOutstandingService.getById(submissionId);
        if(result.equals("通过")){
            //通过就往优秀作业作品表里添加
            OutstandingHomework outstandingHomework = new OutstandingHomework();
            outstandingHomework.setReviewerId(userId);
            outstandingHomework.setSubmissionId(submissionId);
            outstandingHomework.setRecommenderId(unreviewedOutstanding.getRecommenderId());
            outstandingHomeworkService.save(outstandingHomework);
        }
        //无论是不通过还是通过都要从未审批表中删除
        unreviewedOutstandingService.removeById(submissionId);
        return Result.success();
    }
}
