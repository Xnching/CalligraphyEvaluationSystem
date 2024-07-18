package com.moyunzhijiao.system_backend.config;

import com.moyunzhijiao.system_backend.config.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.w3c.dom.ls.LSOutput;

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
                .excludePathPatterns("/api/backend/login","/**/export","/**/import");//排除这些接口，也就是说，这些接口可以放行

    }

    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }
}
