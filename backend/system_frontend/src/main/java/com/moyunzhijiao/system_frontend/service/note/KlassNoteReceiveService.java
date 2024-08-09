package com.moyunzhijiao.system_frontend.service.note;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.entity.homework.Homework;
import com.moyunzhijiao.system_frontend.entity.note.KlassNoteReceive;
import com.moyunzhijiao.system_frontend.entity.note.Note;
import com.moyunzhijiao.system_frontend.entity.note.NoteContent;
import com.moyunzhijiao.system_frontend.entity.note.StudentNoteReceive;
import com.moyunzhijiao.system_frontend.mapper.note.KlassNoteReceiveMapper;
import com.moyunzhijiao.system_frontend.service.homework.HomeworkService;
import com.moyunzhijiao.system_frontend.service.homework.KlassHomeworkService;
import com.moyunzhijiao.system_frontend.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KlassNoteReceiveService extends ServiceImpl<KlassNoteReceiveMapper, KlassNoteReceive> {
    @Autowired
    KlassNoteReceiveMapper klassNoteRecieveMapper;
    @Autowired
    KlassHomeworkService klassHomeworkService;
    @Autowired
    NoteService noteService;
    @Autowired
    TeacherService teacherService;

    @Autowired
    NoteContentService noteContentService;

    /*
    * 催促集体作业
    * */
    @Transactional
    public void urgeHomework(Integer teacherId,Homework homework){
        Integer homeworkId = homework.getId();
        List<Integer> klassIdList = klassHomeworkService.getKlassIdByHomework(homeworkId);
        //创建消息
        Note note = new Note();
        note.setType("作业催促");
        note.setName("您有作业未及时完成！");
        note.setSender(teacherService.getNameById(teacherId));
        note.setAssociationId(homeworkId);
        //设置消息内容
        NoteContent noteContent = new NoteContent();
        noteContent.setMessage("您的老师催促您尽快完成作业："+homework.getName()+"，作业目标群体为："+homework.getTarget()+"，截止时间为："+homework.getDeadline()+"。");
        //插入消息
        noteService.insertSingle(note,noteContent);
        //插入班级和消息之间的关系
        List<KlassNoteReceive> list = klassIdList.stream().map(klassId->{
            KlassNoteReceive klassNoteReceive = new KlassNoteReceive();
            klassNoteReceive.setKlassId(klassId);
            klassNoteReceive.setNoteId(note.getId());
            return klassNoteReceive;
        }).toList();
        //批量保存
        saveBatch(list);
    }

    /*
    * 新增作业
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
        //增加消息和班级之间的关系
        List<KlassNoteReceive>studentNoteReceiveList = list.stream().map(klassId->{
            //设置属性
            KlassNoteReceive klassNoteReceive = new KlassNoteReceive();
            klassNoteReceive.setKlassId(klassId);
            klassNoteReceive.setNoteId(note.getId());
            return klassNoteReceive;
        }).toList();
        //批量保存
        saveBatch(studentNoteReceiveList);
    }
}
