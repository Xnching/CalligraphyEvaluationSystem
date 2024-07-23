package com.moyunzhijiao.system_backend.controller;

import com.moyunzhijiao.system_backend.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/backend/teacher")
public class TeacherController {
    @Autowired
    TeacherService teacherService;

}
