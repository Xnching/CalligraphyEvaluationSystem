package com.moyunzhijiao.system_backend.exception;

import lombok.Getter;

/*
* 自定义的异常
* */
@Getter
public class ServiceException extends RuntimeException{
    private String code;

    public ServiceException(String code,String msg){
        super(msg);
        this.code=code;
    }
}
