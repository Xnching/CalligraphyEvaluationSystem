package com.moyunzhijiao.system_backend.controller;

import com.moyunzhijiao.system_backend.service.TemplateWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/backend/template-word")
public class TemplateWordController {
    @Autowired
    TemplateWordService templateWordService;
}
