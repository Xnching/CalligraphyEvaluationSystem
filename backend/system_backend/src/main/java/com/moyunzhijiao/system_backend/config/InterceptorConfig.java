package com.moyunzhijiao.system_backend.config;

import com.moyunzhijiao.system_backend.component.interceptor.JwtInterceptor;
import com.moyunzhijiao.system_backend.component.interceptor.MyHandshakeInterceptor;
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
        //使用过滤器了，故此处拦截器不再使用
//        registry.addInterceptor(jwtInterceptor())
//                .addPathPatterns("")     //拦截所有请求，通过判断token是否合法来决定是否登录
//                .excludePathPatterns("/api/backend/login"
//                        ,"/upload/**",
//                        "/ws/**"    //此为websocket接口
//                );//排除这些接口，也就是说，这些接口可以放行

    }

    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }
    @Bean
    public MyHandshakeInterceptor handshakeInterceptor(){return new MyHandshakeInterceptor();}
}
