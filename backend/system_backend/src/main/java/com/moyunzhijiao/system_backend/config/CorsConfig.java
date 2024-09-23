package com.moyunzhijiao.system_backend.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


//用于跨域设置,此处配置后就能实现前后端分离中的数据跨域链接
@Configuration
public class CorsConfig {
    //设置当前跨域请求最大有效时长，此处设置默认1天
    public static final long MAX_AGE = 24 * 60 * 60;
    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");    // 1 设置访问源地址
        corsConfiguration.addAllowedHeader("*");    // 2 设置访问源请求头
        corsConfiguration.addAllowedMethod("*");    // 3 设置访问源请求方法
        corsConfiguration.setMaxAge(MAX_AGE);
        source.registerCorsConfiguration("/**",corsConfiguration);      // 4 对接口配置跨域设置
        return new CorsFilter(source);
    }
}
