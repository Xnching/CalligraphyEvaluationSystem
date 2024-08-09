package com.moyunzhijiao.system_frontend.service.note;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.entity.homework.Homework;
import com.moyunzhijiao.system_frontend.entity.note.Note;
import com.moyunzhijiao.system_frontend.entity.note.NoteContent;
import com.moyunzhijiao.system_frontend.entity.note.StudentNoteReceive;
import com.moyunzhijiao.system_frontend.mapper.note.StudentNoteReceiveMapper;
import com.moyunzhijiao.system_frontend.service.homework.HomeworkService;
import com.moyunzhijiao.system_frontend.service.homework.HomeworkSubmissionService;
import com.moyunzhijiao.system_frontend.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentNoteReceiveService extends ServiceImpl<StudentNoteReceiveMapper, StudentNoteReceive> {

    @Autowired
    StudentNoteReceiveMapper studentNoteReceiveMapper;
    @Autowired
    NoteService noteService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    HomeworkSubmissionService homeworkSubmissionService;
    @Autowired
    NoteContentService noteContentService;
    /*
    * 只催促一个人的作业
    * */
    public void urgeHomeworkSingle(Integer teacherId,Integer studentId,Homework homework){
        Integer homeworkId = homework.getId();
        //设置消息
        Note note = new Note();
        note.setType("作业催促");
        note.setName("您有作业未及时完成！");
        note.setSender(teacherService.getNameById(teacherId));
        note.setAssociationId(homeworkId);
        //设置消息内容
        NoteContent noteContent = new NoteContent();
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

    /*
    * 催促作业的生成消息,催促一个个人作业，把里面的所有学生都催促一遍
    * */
    @Transactional
    public void urgeHomeworkBatch(Integer teacherId,Homework homework){
        Integer homeworkId = homework.getId();
        List<Integer> studentList = homeworkSubmissionService.getStudentIdByHomework(homeworkId);
        //只需要一个消息和一个消息内容即可
        Note note = new Note();
        note.setType("作业催促");
        note.setName("您有作业未及时完成！");
        note.setSender(teacherService.getNameById(teacherId));
        note.setAssociationId(homeworkId);
        NoteContent noteContent = new NoteContent();
        noteContent.setMessage("您的老师催促您尽快完成作业："+homework.getName()+"，作业目标群体为："+homework.getTarget()+"，截止时间为："+homework.getDeadline()+"。");
        noteContent.setNoteId(note.getId());
        noteService.insertSingle(note,noteContent);
        //批量生成学生和消息关系，给学生发消息
        List<StudentNoteReceive> studentNoteReceiveList = studentList.stream().map(studentId->{
            StudentNoteReceive studentNoteReceive = new StudentNoteReceive();
            studentNoteReceive.setNoteId(note.getId());
            studentNoteReceive.setStudentId(studentId);
            return studentNoteReceive;
        }).toList();
        saveBatch(studentNoteReceiveList);
    }

    /*
    * 布置作业的时候给学生发消息
    * */
    @Transactional
    public void addHomework(Integer teacherId,List<Integer> list, Homework homework) {
        //新增消息
        Note note = new Note();
        note.setAssociationId(homework.getId());
        note.setType("布置作业");
        note.setSender(teacherService.getNameById(teacherId));
        note.setName("您有新的作业需要完成！");
        noteService.save(note);
        //新增消息内容
        NoteContent noteContent = new NoteContent();
        noteContent.setNoteId(note.getId());
        noteContent.setMessage("您的教师"+note.getSender()+"给您布置了新的作业:"+homework.getName()+",截止时间为："+homework.getDeadline()+",请您在截止时间之前完成此次作业！");
        noteContentService.save(noteContent);
        List<StudentNoteReceive>studentNoteReceiveList = list.stream().map(studentId->{
            StudentNoteReceive studentNoteReceive = new StudentNoteReceive();
            studentNoteReceive.setStudentId(studentId);
            studentNoteReceive.setNoteId(note.getId());
            return studentNoteReceive;
        }).toList();
        saveBatch(studentNoteReceiveList);
    }
}
