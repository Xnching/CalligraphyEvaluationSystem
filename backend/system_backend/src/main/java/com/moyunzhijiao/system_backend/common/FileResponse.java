package com.moyunzhijiao.system_backend.common;

import lombok.Data;


public class FileResponse {
    private int errno;  //0为成功，1为失败
    private Data data;
    private String message;

    public FileResponse(int errno, String url, String alt, String href) {
        this.errno = errno;
        this.data = new Data(url, alt, href);
    }
    public FileResponse(int errno,String message){
        this.errno = errno;
        this.message=message;
    }

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        private String url;         // 图片 src ，必须
        private String alt;         // 图片描述文字，非必须
        private String href;        // 图片的链接，非必须

        public Data(String url, String alt, String href) {
            this.url = url;
            this.alt = alt;
            this.href = href;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }
    }

    // url必须返回
    public static FileResponse success(String url) {
        return new FileResponse(0, "", "", "");
    }

    // 有参返回正确
    public static FileResponse success(String url, String alt, String href) {
        return new FileResponse(0, url, alt, href);
    }

    // 返回失败
    public static FileResponse error(String msg) {
        return new FileResponse(1, msg);
    }

    // 默认一个错误类
    public static FileResponse error() {
        return new FileResponse(1, "系统错误");
    }
}
