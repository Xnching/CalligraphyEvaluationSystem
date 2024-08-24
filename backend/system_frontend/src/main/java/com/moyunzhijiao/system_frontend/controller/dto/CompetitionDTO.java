package com.moyunzhijiao.system_frontend.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class CompetitionDTO {
    String id;
    String name;
    String pictureUrl;
    String requirements;
    String startTime;
    String score;
    List<DivisionDTO> division;
    Boolean disabled;//是否报名该竞赛
}
