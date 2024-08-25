package com.moyunzhijiao.system_backend.controller.competition;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.moyunzhijiao.system_backend.common.Constants;
import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.entiy.competition.Division;
import com.moyunzhijiao.system_backend.service.competition.ReviewersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/backend/review-work")
public class ReviewWorkController {
    @Autowired
    ReviewersService reviewersService;
    /*
     * 根据一个教师的id获取该教师的要评阅的所有组别
     * */
    @GetMapping("/teacher")
    public Result getDivisionOfTeacher(@RequestHeader("token") String token){
        // 解码 token
        DecodedJWT jwt = JWT.decode(token);
        // 从载荷中获取用户 ID
        Integer teacherId = Integer.valueOf(jwt.getAudience().get(0));
        String userType = jwt.getClaim("userType").asString();
        if(userType.equals("系统用户")){
            return Result.error(Constants.CODE_400,"您不是评阅教师，您无法观看使用此界面！");
        }
        List<Division> list = reviewersService.getDivisionOfTeacher(teacherId);
        return Result.success(list);
    }
}
