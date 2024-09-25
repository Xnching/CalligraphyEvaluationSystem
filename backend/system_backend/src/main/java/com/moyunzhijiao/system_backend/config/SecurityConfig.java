package com.moyunzhijiao.system_backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.component.CustomMd5PasswordEncoder;
import com.moyunzhijiao.system_backend.component.filter.JwtAuthenticationTokenFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import static com.moyunzhijiao.system_backend.config.CorsConfig.MAX_AGE;

@Configuration
/**
 * @EnableWebSecurity是开启SpringSecurity的默认行为
 */
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Autowired
    CorsConfig corsConfig;

    /*
    * 密码明文加密方式配置
    * */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new CustomMd5PasswordEncoder();
    }

    /*
    * 获取AuthenticationManager（认证管理器），登录时认证使用
    * */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /*
     *  配置跨源访问(CORS)
     * @return
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");    // 1 设置访问源地址
        corsConfiguration.addAllowedHeader("*");    // 2 设置访问源请求头
        corsConfiguration.addAllowedMethod("*");    // 3 设置访问源请求方法
        corsConfiguration.setMaxAge(MAX_AGE);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);      // 4 对接口配置跨域设置
        return source;
    }

    //我们自定义的拦截器
    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 基于 token，不需要 csrf
                .csrf(AbstractHttpConfigurer::disable)
                // 开启跨域以便前端调用接口
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // 配置 CORS
                .authorizeHttpRequests(authorize -> authorize
                        // 指定某些接口不需要通过验证即可访问。登录接口肯定是不需要认证的
                        .requestMatchers("/api/backend/login","/ws/**").permitAll()
                        .requestMatchers("/upload/**","/doc.html").permitAll()
                        // 这里意思是其它所有接口需要认证才能访问
                        .anyRequest().authenticated()
                )
                // 基于 token，不需要 session
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            System.out.println(request);
                            System.out.println(response);
                            System.out.println(exception);
                            authException.printStackTrace();
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json;charset=UTF-8");
                            Result s = Result.error("401", "未认证用户");
                            String r = new ObjectMapper().writeValueAsString(s);
                            response.getWriter().println(r);
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            System.out.println(request);
                            System.out.println(response);
                            System.out.println(exception);
                            accessDeniedException.printStackTrace();
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.setContentType("application/json;charset=UTF-8");
                            Result s = Result.error("403", "权限不足，无法访问系统资源");
                            String r = new ObjectMapper().writeValueAsString(s);
                            response.getWriter().println(r);
                        })
                );
        //将我们的JWT filter添加到UsernamePasswordAuthenticationFilter前面，因为这个Filter是authentication开始的filter，我们要早于它
        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
