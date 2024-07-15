package com.moyunzhijiao.system_backend.config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.moyunzhijiao.system_backend.common.Constants;
import com.moyunzhijiao.system_backend.entiy.User;
import com.moyunzhijiao.system_backend.exception.ServiceException;
import com.moyunzhijiao.system_backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/*
* 拦截器，拦截token
* */
@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request , HttpServletResponse response,Object handler) throws Exception{
        String token = request.getHeader("token");
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        //执行认证
        if(StrUtil.isBlank(token)){
            throw new ServiceException(Constants.CODE_401,"无token验证失败");
        }
        //获取token中的userId
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException e) {
            e.printStackTrace();
            String errMsg = "token验证失败，请重新登录";
            throw new ServiceException(Constants.CODE_401,errMsg);
        }
        //根据token中的userId查询数据库
        User user = userService.getById(userId);
        if(user == null){
            throw new ServiceException(Constants.CODE_401,"用户不存在，请重新登录");
        }
        // 用户密码加签验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token);  //验证token
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            throw new ServiceException(Constants.CODE_401,"token验证失败，请重新登录");
        }
        return true;
    }
}
