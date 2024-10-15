package com.moyunzhijiao.system_app.controller.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadDTO {
    Integer userId;
    Integer exerciseId;
    String[] content;

    public UploadDTO(Integer userId, Integer exerciseId, String[] files) {
        this.userId = userId;
        this.exerciseId = exerciseId;
        this.content = files;
    }
}
