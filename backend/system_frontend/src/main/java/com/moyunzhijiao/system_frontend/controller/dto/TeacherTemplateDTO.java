package com.moyunzhijiao.system_frontend.controller.dto;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_frontend.entity.template.CustomTemplate;
import com.moyunzhijiao.system_frontend.entity.template.SystemTemplate;
import lombok.Data;

@Data
public class TeacherTemplateDTO {
    IPage<CustomTemplate> self;
    IPage<SystemTemplate> system;
    IPage<CustomTemplate> existHomework;
    IPage<CustomTemplate> collection;
}
