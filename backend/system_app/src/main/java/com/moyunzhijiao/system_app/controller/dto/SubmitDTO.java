package com.moyunzhijiao.system_app.controller.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SubmitDTO {
    Integer userId;
    Integer competitionId;
    String[] content;

    public SubmitDTO(Integer userId, Integer competitionId, String[] files) {
        this.userId = userId;
        this.competitionId = competitionId;
        this.content = files;
    }
}
