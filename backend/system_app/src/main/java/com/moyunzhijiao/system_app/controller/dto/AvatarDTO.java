package com.moyunzhijiao.system_app.controller.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AvatarDTO {
    Integer userId;
    String avatar;

    public AvatarDTO(Integer userId, String file) {
        this.userId = userId;
        this.avatar = file;
    }
}
