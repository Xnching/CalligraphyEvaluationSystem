package com.moyunzhijiao.system_frontend.service;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Getter
public class ConfigService {
    @Value("${server.protocol}")
    private String serverProtocol;

    @Value("${server.ip}")
    private String serverIp;

    @Value("${server.port}")
    private String serverPort;

    private static String baseUrl;

    //FileController里的也要改
    private static String avatarFilePath;
    private static String avatarUrl;
    private static final String AVATAR_BASE_URL = "/upload/images/avatar";
    @PostConstruct
    public void init() {
        baseUrl = serverProtocol + "://" + serverIp + ":" + serverPort;
        File projectRoot = new File(System.getProperty("user.dir"));
        //头像文件路径
        avatarFilePath = projectRoot.getParentFile().getParent()+"/resources/image/avatar/";
        //头像接口url
        avatarUrl = baseUrl+AVATAR_BASE_URL;
    }
    public static String getBaseUrl() {
        return baseUrl;
    }
    public static String getAvatarFilePath(){
        return avatarFilePath;
    }
    public static String getAvatarUrl(){
        return avatarUrl;
    }


}
