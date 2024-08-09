package com.moyunzhijiao.system_frontend.controller;

import com.moyunzhijiao.system_frontend.common.Result;
import com.moyunzhijiao.system_frontend.service.note.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoteController {
    @Autowired
    NoteService noteService;

    @GetMapping("/ciep/message")
    public Result findMessageOfTeacher(){
        return Result.success();
    }
}
