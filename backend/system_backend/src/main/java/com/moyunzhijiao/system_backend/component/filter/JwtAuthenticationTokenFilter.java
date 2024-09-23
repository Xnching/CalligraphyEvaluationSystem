package com.moyunzhijiao.system_backend.component.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.moyunzhijiao.system_backend.common.Constants;
import com.moyunzhijiao.system_backend.entiy.back.User;
import com.moyunzhijiao.system_backend.entiy.front.Teacher;
import com.moyunzhijiao.system_backend.exception.ServiceException;
import com.moyunzhijiao.system_backend.service.UserDetailsServiceImpl;
import com.moyunzhijiao.system_backend.service.back.UserService;
import com.moyunzhijiao.system_backend.service.front.TeacherService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private final TeacherService teacherService;
    private final UserService userService;
    private final UserDetailsServiceImpl userDetailsServiceImp;
    @Autowired
    public JwtAuthenticationTokenFilter(TeacherService teacherService,UserService userService,UserDetailsServiceImpl userDetailsServiceImp){
        this.teacherService = teacherService;
        this.userService = userService;
        this.userDetailsServiceImp = userDetailsServiceImp;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            //没token，抛异常
            throw new ServiceException(Constants.CODE_401,"无token验证失败");
        }
        //获取token中的userId
        String userId;
        String userType;
        UsernamePasswordAuthenticationToken authenticationToken;
        try {
            userId = JWT.decode(token).getAudience().get(0);
            userType = JWT.decode(token).getClaim("userType").asString();
        } catch (JWTDecodeException e) {
            e.printStackTrace();
            throw new ServiceException(Constants.CODE_401,"token验证失败，请重新登录");
        }
        if(userType.equals("系统用户")){
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
            authenticationToken = new UsernamePasswordAuthenticationToken(user,null,userDetailsServiceImp.getPermission(userType, user.getUserGroupId()));
        }else if(userType.equals("教师")){
            Teacher teacher = teacherService.getById(userId);
            if(teacher == null){
                throw new ServiceException(Constants.CODE_401,"用户不存在，请重新登录");
            }
            // 用户密码加签验证 token
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(teacher.getPassword())).build();
            try {
                jwtVerifier.verify(token);  //验证token
            } catch (JWTVerificationException e) {
                e.printStackTrace();
                throw new ServiceException(Constants.CODE_401,"token验证失败，请重新登录");
            }
            authenticationToken = new UsernamePasswordAuthenticationToken(teacher,null,userDetailsServiceImp.getPermission(userType,null));
        }else {
            throw new ServiceException(Constants.CODE_401,"您的用户身份非法！");
        }
        //存入SecurityContextHolder
        //TODO 获取权限信息封装到Authentication中
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //放行
        filterChain.doFilter(request, response);
    }
}
