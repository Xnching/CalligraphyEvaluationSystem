package com.moyunzhijiao.system_backend.controller.outstanding;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.controller.dto.homework.HomeworkSubmissionDTO;
import com.moyunzhijiao.system_backend.controller.dto.outstanding.OutstandingHomeworkDTO;
import com.moyunzhijiao.system_backend.service.homework.HomeworkSubmissionService;
import com.moyunzhijiao.system_backend.service.outstanding.OutstandingHomeworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/backend/outstanding-homework")
@PreAuthorize("hasAuthority('优秀作业作品')")
public class OutstandingHomeworkController {
    @Autowired
    OutstandingHomeworkService outstandingHomeworkService;
    @Autowired
    HomeworkSubmissionService homeworkSubmissionService;
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "")String str){
        IPage<OutstandingHomeworkDTO> page = new Page<>(pageNum,pageNum);
        page = outstandingHomeworkService.getPage(page,str);
        System.out.println("让我看下"+page);
        return Result.success(page);
    }
    @GetMapping("/detail")
    public Result getDetail(@RequestParam Integer submissionId){
        HomeworkSubmissionDTO homeworkSubmissionDTO = homeworkSubmissionService.getDetail(submissionId);
        return Result.success(homeworkSubmissionDTO);
    }

    @DeleteMapping("/delete")
    public Result deleteOutstanding(@RequestBody Map<String,String> r){
        Integer submissionId = Integer.valueOf(r.get("id"));
        outstandingHomeworkService.removeById(submissionId);
        return Result.success();
    }
}
