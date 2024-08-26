package com.moyunzhijiao.system_backend.controller.competition;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.entiy.competition.CompetitionSubmissions;
import com.moyunzhijiao.system_backend.service.competition.CompetitionSubmissionsService;
import com.moyunzhijiao.system_backend.service.competition.FinalRankService;
import com.moyunzhijiao.system_backend.service.competition.FinalReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/backend/detailed-review")
public class DetailedReviewController {
    @Autowired
    CompetitionSubmissionsService competitionSubmissionsService;
    @Autowired
    FinalReviewService finalReviewService;
    @GetMapping("/initial-page")
    public Result getInitialPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize
                                ,@RequestParam Integer teacherId,@RequestParam Integer divisionId){
        IPage<CompetitionSubmissions> page = new Page<>(pageNum,pageSize);
        page = competitionSubmissionsService.getInitialOfTeacher(page,teacherId,divisionId);
        return Result.success(page);
    }

    @GetMapping("/final-page")
    public Result getFinalPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                               @RequestParam Integer teacherId,@RequestParam Integer divisionId){
        IPage<CompetitionSubmissions> page = new Page<>(pageNum,pageSize);
        page = finalReviewService.getFinalOfTeacher(page,teacherId,divisionId);
        return Result.success(page);
    }
}
