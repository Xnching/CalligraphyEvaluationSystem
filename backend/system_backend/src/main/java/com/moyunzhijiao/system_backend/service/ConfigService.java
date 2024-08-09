package com.moyunzhijiao.system_backend.service;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Getter
public class ConfigService {
    @Value("${server.protocol}")
    private String serverProtocol;      //协议，例如http

    @Value("${server.ip}")
    private String serverIp;            //服务器ip地址

    @Value("${server.port}")
    private String serverPort;          //端口

    private static String baseUrl;

    //FileController里的也要改
    //模板字的API接口
    private static String templateWordFilePath;
    private static String templateWordUrl;
    private static final String TEMPLATE_WORD_BASE_URL = "/upload/images/templateWord";
    private static final String TEMPLATE_WORD_PATH = "/resources/image/templateWord/";

    //样本字的
    private static String sampleWordFilePath;
    private static String sampleWordUrl;
    private static final String SAMPLE_WORD_BASE_URL = "/upload/images/sampleWord";
    private static final String SAMPLE_WORD_PATH = "/resources/image/sampleWord/";


    //字帖的
    private static String copybookFilePath;
    private static String copybookUrl;
    private static final String COPYBOOK_BASE_URL = "/upload/images/copybook";
    private static final String COPYBOOK_PATH = "/resources/image/copybook/";

    @PostConstruct
    public void init() {
        baseUrl = serverProtocol + "://" + serverIp + ":" + serverPort;
        File projectRoot = new File(System.getProperty("user.dir"));


        //设置模板字
        templateWordFilePath = projectRoot.getParentFile().getParent()+TEMPLATE_WORD_PATH;
        templateWordUrl = baseUrl+TEMPLATE_WORD_BASE_URL;

        //设置样本字
        sampleWordFilePath = projectRoot.getParentFile().getParent()+SAMPLE_WORD_PATH;
        sampleWordUrl = baseUrl+SAMPLE_WORD_BASE_URL;

        //设置字帖
        copybookFilePath = projectRoot.getParentFile().getParent()+COPYBOOK_PATH;
        copybookUrl = baseUrl+COPYBOOK_BASE_URL;
    }
    public static String getBaseUrl() {
        return baseUrl;
    }

    public static String getTemplateWordFilePath() {
        return templateWordFilePath;
    }

    public static String getSampleWordFilePath(){
        return sampleWordFilePath;
    }

    public static String getCopybookFilePath(){
        return copybookFilePath;
    }

    public static String getCopybookUrl(){
        return copybookUrl;
    }

    public static String getSampleWordUrl(){
        return sampleWordUrl;
    }


}
