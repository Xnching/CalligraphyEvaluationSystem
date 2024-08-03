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
    private static String avatorFilePath;
    private static String avatorUrl;
    private static final String AVATOR_BASE_URL = "/upload/images/avator";
    @PostConstruct
    public void init() {
        baseUrl = serverProtocol + "://" + serverIp + ":" + serverPort;
        File projectRoot = new File(System.getProperty("user.dir"));
        //头像文件路径
        avatorFilePath = projectRoot.getParentFile().getParent()+"/resources/image/avator/";
        //头像接口url
        avatorUrl = baseUrl+AVATOR_BASE_URL;
    }
    public static String getBaseUrl() {
        return baseUrl;
    }
    public static String getAvatorFilePath(){
        return avatorFilePath;
    }
    public static String getAvatorUrl(){
        return avatorUrl;
    }


}
