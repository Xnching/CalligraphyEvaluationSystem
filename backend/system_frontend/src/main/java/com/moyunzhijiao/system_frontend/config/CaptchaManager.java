package com.moyunzhijiao.system_frontend.config;

import lombok.Getter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CaptchaManager {

    public final Map<String, Captcha> captchaMap = new ConcurrentHashMap<>();

    public void addCaptcha(String uuid, String captchaContent) {
        captchaMap.put(uuid, new Captcha(captchaContent, System.currentTimeMillis()));
    }

    public boolean validateCaptcha(String uuid, String captchaContent) {
        Captcha captcha = captchaMap.get(uuid);
        if (captcha != null && captcha.getContent().equals(captchaContent)) {
            captchaMap.remove(uuid);
            return true;
        }
        return false;
    }

    @Scheduled(fixedRate = 60000) // 每分钟执行一次
    public void cleanExpiredCaptchas() {
        long now = System.currentTimeMillis();
        // 5分钟过期时间
        captchaMap.entrySet().removeIf(entry -> now - entry.getValue().getTimestamp() > 300000);
    }
    public Map<String, Captcha> getCaptchaMap(){
        return captchaMap;
    }

    @Getter
    public static class Captcha {
        private final String content;
        private final long timestamp;

        public Captcha(String content, long timestamp) {
            this.content = content;
            this.timestamp = timestamp;
        }

    }
}