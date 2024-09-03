package com.moyunzhijiao.system_backend.service.front;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.config.handler.MyWebSocketHandler;
import com.moyunzhijiao.system_backend.entiy.competition.Competition;
import com.moyunzhijiao.system_backend.entiy.front.Note;
import com.moyunzhijiao.system_backend.entiy.front.NoteContent;
import com.moyunzhijiao.system_backend.mapper.front.NoteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService extends ServiceImpl<NoteMapper, Note> {
    @Autowired
    NoteContentService noteContentService;
    @Autowired
    MyWebSocketHandler myWebSocketHandler;
    public void addCompetition(Competition competition) {
        Note note = new Note();
        NoteContent noteContent = new NoteContent();
        note.setType("竞赛消息");
        note.setTarget("全部");
        myWebSocketHandler.sendMessageToAll("有新消息");
        note.setName("竞赛:"+competition.getName()+"即将举办！");
        note.setSender("官方");
        note.setAssociationId(competition.getId());
        save(note);
        noteContent.setNoteId(note.getId());
        noteContent.setMessage("亲爱的学生/教师，\n" +
                "\n" +
                "我们非常高兴地通知您，竞赛 "+competition.getName()+" 即将举办，并且报名工作即将开始！经过一段时间的精心准备和筹备，我们终于迎来了这个激动人心的时刻。\n" +
                "\n" +
                "本次竞赛旨在为大家提供一个展示才华、交流经验和提升自我的平台。我们相信，通过这次竞赛，您将有机会与其他优秀的参赛者切磋技艺，互相学习，共同进步。\n" +
                "\n" +
                "竞赛的具体安排如下：\n" +
                "\n" +
                "竞赛名称："+competition.getName()+"\n" +
                "报名开始时间："+competition.getRegistrationStartTime()+"\n" +
                "报名结束时间："+competition.getRegistrationEndTime()+"\n" +
                "竞赛时间："+competition.getCompetitionStartTime()+" 至 "+competition.getCompetitionEndTime()+"\n" +
                "在报名期间，我们将为您提供必要的支持和帮助，确保每一位参赛者都能顺利完成报名。请您务必遵守报名规则，按时提交报名材料，展现出良好的竞技精神和团队合作精神。\n" +
                "\n" +
                "我们期待看到您在竞赛中的精彩表现，并预祝您取得优异的成绩！如果您有任何问题或需要进一步的帮助，请随时与我们联系。\n" +
                "\n" +
                "再次感谢您对本次竞赛的支持与参与。祝您在竞赛中取得圆满成功！");
        noteContentService.save(noteContent);
    }

    public void endCompetition(Competition competition) {
        Note note = new Note();
        NoteContent noteContent = new NoteContent();
        note.setType("竞赛消息");
        note.setTarget("全部");
        note.setName("竞赛:"+competition.getName()+"已落下帷幕！");
        note.setSender("官方");
        note.setAssociationId(competition.getId());
        save(note);
        noteContent.setNoteId(note.getId());
        noteContent.setMessage("亲爱的学生/教师，\n" +
                "\n" +
                "我们非常高兴地通知您，竞赛: "+competition.getName()+"已经顺利完成评阅工作，现已正式进入已结束阶段。在此过程中，我们见证了您在竞赛中的出色表现和积极参与，深感欣慰。\n" +
                "\n" +
                "本次竞赛吸引了众多才华横溢的学生和敬业的教师参与，大家在竞赛中展现了卓越的能力和团队合作精神。无论是参赛者的精彩表现，还是评委们的辛勤工作，都为本次竞赛增添了许多亮点。\n" +
                "\n" +
                "我们深知，竞赛的成功离不开每一位参与者的努力和付出。感谢您在整个竞赛过程中的积极参与和支持。您的努力不仅为自己赢得了荣誉，也为整个团队和学校增光添彩。\n" +
                "\n" +
                "在未来的日子里，我们期待您继续保持这种积极向上的态度，勇于挑战自我，不断追求卓越。无论是在学术上还是在生活中，我们都希望您能够取得更大的成就。\n" +
                "\n" +
                "再次感谢您对本次竞赛的支持与参与。祝您在未来的学习和工作中一切顺利，取得更加辉煌的成绩！");
        noteContentService.save(noteContent);
    }
}
