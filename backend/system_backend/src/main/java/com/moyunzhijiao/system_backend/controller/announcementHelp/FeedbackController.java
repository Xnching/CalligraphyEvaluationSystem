package com.moyunzhijiao.system_backend.controller.announcementHelp;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.entiy.announcementHelp.Feedback;
import com.moyunzhijiao.system_backend.entiy.announcementHelp.FeedbackContent;
import com.moyunzhijiao.system_backend.entiy.back.User;
import com.moyunzhijiao.system_backend.service.announcementHelp.FeedbackContentService;
import com.moyunzhijiao.system_backend.service.announcementHelp.FeedbackService;
import com.moyunzhijiao.system_backend.service.back.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/backend/feedback")
@PreAuthorize("hasAuthority('建议与反馈管理')")
public class FeedbackController {
    @Autowired
    FeedbackService feedbackService;
    @Autowired
    FeedbackContentService feedbackContentService;
    @Autowired
    UserService userService;
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize){
        IPage<Feedback> page = new Page<>(pageNum,pageSize);
        page = feedbackService.page(page);
        return Result.success(page);
    }

    @GetMapping("/detail")
    public Result getDetail(@RequestParam Integer id){
        FeedbackContent feedbackContent = feedbackContentService.getById(id);
        return Result.success(feedbackContent);
    }
    @PutMapping("/reply")
    public Result replyFeedback(@RequestHeader("token") String token,@RequestBody Map<String, String> params){
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        User user = userService.getById(userId);
        String reply = params.get("reply");
        String id = params.get("id");
        FeedbackContent feedbackContent = feedbackContentService.getById(id);
        feedbackContent.setReply(reply);
        feedbackContentService.updateById(feedbackContent);
        Feedback feedback = feedbackService.getById(id);
        feedback.setState("已回复");
        feedback.setEditor(user.getName());
        feedbackService.updateById(feedback);
        return Result.success();
    }
    @PutMapping("/state")
    public Result setState(@RequestHeader("token") String token,@RequestBody Map<String, String> params){
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        User user = userService.getById(userId);
        String id = params.get("id");
        Feedback feedback = feedbackService.getById(id);
        feedback.setState("无效");
        feedback.setEditor(user.getName());
        feedbackService.updateById(feedback);
        return Result.success();
    }
    @DeleteMapping("/delete")
    public Result deleteFeedback(@RequestBody Map<String,String> r){
        Integer id = Integer.valueOf(r.get("id"));
        feedbackService.removeById(id);
        feedbackContentService.removeById(id);
        return Result.success();
    }
}
