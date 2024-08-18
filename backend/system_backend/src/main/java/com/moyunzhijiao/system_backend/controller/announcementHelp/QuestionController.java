package com.moyunzhijiao.system_backend.controller.announcementHelp;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.entiy.announcementHelp.Question;
import com.moyunzhijiao.system_backend.entiy.back.User;
import com.moyunzhijiao.system_backend.service.announcementHelp.QuestionService;
import com.moyunzhijiao.system_backend.service.back.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/backend/question")
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @Autowired
    UserService userService;
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize){
        IPage<Question> page = new Page<>(pageNum,pageSize);
        page = questionService.page(page);
        return Result.success(page);
    }

    @PostMapping("/add")
    public Result addQuestion(@RequestHeader("token") String token,@RequestBody Question question){
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        User user = userService.getById(userId);
        question.setEditor(user.getName());
        questionService.save(question);
        return Result.success();
    }

    @PutMapping("/update")
    public Result updateQuestion(@RequestHeader("token") String token,@RequestBody Question question){
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer userId = Integer.valueOf(jwt.getAudience().get(0));
        User user = userService.getById(userId);
        question.setEditor(user.getName());
        questionService.updateById(question);
        return Result.success();
    }
    @DeleteMapping("/delete")
    public Result deleteQuestion(@RequestBody Map<String,String> r){
        Integer id = Integer.valueOf(r.get("id"));
        questionService.removeById(id);
        return Result.success();
    }
}
