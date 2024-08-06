package com.moyunzhijiao.system_frontend.service.note;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.entity.Homework;
import com.moyunzhijiao.system_frontend.entity.note.Note;
import com.moyunzhijiao.system_frontend.entity.note.NoteContent;
import com.moyunzhijiao.system_frontend.entity.note.StudentNoteReceive;
import com.moyunzhijiao.system_frontend.mapper.note.StudentNoteReceiveMapper;
import com.moyunzhijiao.system_frontend.service.HomeworkService;
import com.moyunzhijiao.system_frontend.service.HomeworkSubmissionService;
import com.moyunzhijiao.system_frontend.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentNoteReceiveService extends ServiceImpl<StudentNoteReceiveMapper, StudentNoteReceive> {

    @Autowired
    StudentNoteReceiveMapper studentNoteReceiveMapper;
    @Autowired
    NoteService noteService;
    @Autowired
    HomeworkService homeworkService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    HomeworkSubmissionService homeworkSubmissionService;
    @Autowired
    NoteContentService noteContentService;
    public void urgeHomeworkSingle(Integer teacherId,Integer studentId,Integer homeworkId){
        //设置消息
        Note note = new Note();
        note.setType("作业催促");
        note.setName("您有作业未及时完成！");
        note.setSender(teacherService.getNameById(teacherId));
        note.setAssociationId(homeworkId);
        //设置消息内容
        NoteContent noteContent = new NoteContent();
        Homework homework = homeworkService.getById(homeworkId);
        noteContent.setMessage("您的老师催促您尽快完成作业："+homework.getName()+"，作业目标群体为："+homework.getTarget()+"，截止时间为："+homework.getDeadline()+"。");
        //单个插入消息
        noteService.insertSingle(note,noteContent);
        //设置关系
        StudentNoteReceive studentNoteReceive = new StudentNoteReceive();
        studentNoteReceive.setNoteId(note.getId());
        studentNoteReceive.setStudentId(studentId);
        //单个插入关系
        studentNoteReceiveMapper.insert(studentNoteReceive);
    }

    @Transactional
    public void urgeHomeworkBatch(Integer teacherId,Integer homeworkId){
        Homework homework = homeworkService.getById(homeworkId);
        List<Integer> studentList = homeworkSubmissionService.getStudentIdByHomework(homeworkId);
        List<Note> noteList = new ArrayList<>();
        List<NoteContent> noteContentList = new ArrayList<>();
        List<StudentNoteReceive> studentNoteReceiveList = new ArrayList<>();
        for(Integer studentId:studentList){
            //设置消息
            Note note = new Note();
            note.setType("作业催促");
            note.setName("您有作业未及时完成！");
            note.setSender(teacherService.getNameById(teacherId));
            note.setAssociationId(homeworkId);
            noteList.add(note);
        }
        noteService.saveBatch(noteList);
        Note note;
        //要先把消息保存了才能获得消息的id，所以要两次循环studentList大小的次数
        for (int i=0;i<studentList.size();i++){
            note = noteList.get(i);
            Integer studentId = studentList.get(i);
            //设置消息内容
            NoteContent noteContent = new NoteContent();
            noteContent.setMessage("您的老师催促您尽快完成作业："+homework.getName()+"，作业目标群体为："+homework.getTarget()+"，截止时间为："+homework.getDeadline()+"。");
            noteContent.setNoteId(note.getId());
            noteContentList.add(noteContent);
            //设置关系
            StudentNoteReceive studentNoteReceive = new StudentNoteReceive();
            studentNoteReceive.setStudentId(studentId);
            studentNoteReceive.setNoteId(note.getId());
            studentNoteReceiveList.add(studentNoteReceive);
        }
        noteContentService.saveBatch(noteContentList);
        saveBatch(studentNoteReceiveList);
    }
}
