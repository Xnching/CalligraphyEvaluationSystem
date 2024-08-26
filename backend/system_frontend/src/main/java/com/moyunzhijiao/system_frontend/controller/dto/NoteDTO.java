package com.moyunzhijiao.system_frontend.controller.dto;

import lombok.Data;

@Data
public class NoteDTO {
    Integer id             ;
    String name           ;
    String type           ;
    String sender         ;
    Integer associationId ;
    String createdTime   ;
    String target         ;
    String content;
}
