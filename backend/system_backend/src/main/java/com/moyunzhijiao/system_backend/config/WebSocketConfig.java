package com.moyunzhijiao.system_backend.config;

import com.moyunzhijiao.system_backend.component.handler.MyWebSocketHandler;
import com.moyunzhijiao.system_backend.component.interceptor.MyHandshakeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new MyWebSocketHandler(), "/ws")    //路径为：ws://192.168.43.250:8084/ws?userId=12345&userType=admin
                .addInterceptors(new MyHandshakeInterceptor())
                .setAllowedOrigins("*");
    }
}