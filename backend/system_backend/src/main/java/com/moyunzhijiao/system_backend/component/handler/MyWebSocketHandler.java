package com.moyunzhijiao.system_backend.component.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class MyWebSocketHandler extends TextWebSocketHandler {
    private static final CopyOnWriteArrayList<WebSocketSession> studentSessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session){
        studentSessions.add(session);
        System.out.println("目前有"+studentSessions.size()+"人正在连线中。。。");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message){
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status){
        studentSessions.remove(session);
    }


    // 向指定客户端发送消息
    public void sendMessageToAll(String message) {
        for (WebSocketSession session : studentSessions) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
