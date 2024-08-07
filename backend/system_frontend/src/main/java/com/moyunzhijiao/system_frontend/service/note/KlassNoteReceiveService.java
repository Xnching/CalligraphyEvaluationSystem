package com.moyunzhijiao.system_frontend.service.note;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_frontend.entity.homework.Homework;
import com.moyunzhijiao.system_frontend.entity.note.KlassNoteReceive;
import com.moyunzhijiao.system_frontend.entity.note.Note;
import com.moyunzhijiao.system_frontend.entity.note.NoteContent;
import com.moyunzhijiao.system_frontend.mapper.note.KlassNoteReceiveMapper;
import com.moyunzhijiao.system_frontend.service.homework.HomeworkService;
import com.moyunzhijiao.system_frontend.service.homework.KlassHomeworkService;
import com.moyunzhijiao.system_frontend.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    HomeworkService homeworkService;
    @Transactional
    public void urgeHomework(Integer teacherId,Integer homeworkId){
        List<Integer> klassIdList = klassHomeworkService.getKlassIdByHomework(homeworkId);
        for (Integer klassId: klassIdList) {
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
            //插入消息
            noteService.insertSingle(note,noteContent);
            //插入班级和消息之间的关系
            KlassNoteReceive klassNoteReceive = new KlassNoteReceive();
            klassNoteReceive.setKlassId(klassId);
            klassNoteReceive.setNoteId(note.getId());
            klassNoteRecieveMapper.insert(klassNoteReceive);
        }
    }
}
