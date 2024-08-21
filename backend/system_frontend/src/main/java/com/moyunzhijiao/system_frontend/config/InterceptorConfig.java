package com.moyunzhijiao.system_frontend.config;

import com.moyunzhijiao.system_frontend.config.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * 使用前面已经定义好的JwtInterceptor，然后进行拦截和放行设置。
 * */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    // 加自定义拦截器JwtInterceptor，设置拦截规则
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**")     //拦截所有请求，通过判断token是否合法来决定是否登录
                .excludePathPatterns("/ciep/login","/cieps/login","/api/frontend/student/login",
                        "/doc.html", "/**/import", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-resources/**",
                        "/webjars/**", "/swagger-ui/**","/upload/**"); // 放行Knife4j相关路径

    }

    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }
}