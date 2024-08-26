package com.moyunzhijiao.system_frontend.mapper.note;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_frontend.controller.dto.NoteDTO;
import com.moyunzhijiao.system_frontend.entity.note.Note;
import lombok.Data;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


public interface NoteMapper extends BaseMapper<Note> {
    @Select("SELECT n.*, nc.message as content FROM note n JOIN note_content nc ON n.id = nc.note_id WHERE n.target LIKE '%学生%' or n.target LIKE '%全体%'")
    public IPage<NoteDTO> getSysMsgOfStu(IPage<NoteDTO> page);

    @Select("SELECT count(*) FROM note WHERE target LIKE '%学生%' or target LIKE '%全体%'")
    public Integer countSysMsgOfStu();

    @Select("SELECT n.*, nc.message as content FROM note n JOIN note_content nc ON n.id = nc.note_id WHERE n.target LIKE '%小学%' or n.target LIKE '%全部%'")
    public IPage<NoteDTO> getComMsgOfStu(IPage<NoteDTO> page);

    @Select("SELECT count(*) FROM note WHERE target LIKE '%小学%' or target LIKE '%全部%'")
    public Integer countComMsgOfStu();

    @Select("SELECT " +
            "    n.*, " +
            "    nc.message as content "+
            "FROM  " +
            "    note n " +
            "JOIN " +
            "    note_content nc ON n.id = nc.note_id "+
            "WHERE  " +
            "    n.type = '布置作业' " +
            "AND  " +
            "    ( " +
            "        n.id IN (SELECT note_id FROM student_note_receive WHERE student_id = #{stuId} )  " +
            "        OR  " +
            "        n.id IN (SELECT note_id FROM klass_note_receive WHERE klass_id = #{klassId} ) " +
            "    )")
    public IPage<NoteDTO> getWorkReleaseMsgOfStu(IPage<NoteDTO> page, @Param("stuId") Integer stuId, @Param("klassId") Integer klassId);

    @Select("SELECT " +
            "    count(*) " +
            "FROM  " +
            "    note n " +
            "WHERE  " +
            "    n.type = '布置作业' " +
            "AND  " +
            "    ( " +
            "        n.id IN (SELECT note_id FROM student_note_receive WHERE student_id = #{stuId} )  " +
            "        OR  " +
            "        n.id IN (SELECT note_id FROM klass_note_receive WHERE klass_id = #{klassId} ) " +
            "    )")
    public Integer countWorkReleaseMsgOfStu(@Param("stuId") Integer stuId, @Param("klassId") Integer klassId);

    @Select("SELECT " +
            "    n.*, " +
            "    nc.message as content "+
            "FROM  " +
            "    note n " +
            "JOIN " +
            "    note_content nc ON n.id = nc.note_id "+
            "WHERE  " +
            "    n.type = '作业催促' " +
            "AND  " +
            "    ( " +
            "        n.id IN (SELECT note_id FROM student_note_receive WHERE student_id = #{stuId} )  " +
            "        OR  " +
            "        n.id IN (SELECT note_id FROM klass_note_receive WHERE klass_id = #{klassId} ) " +
            "    )")
    public IPage<NoteDTO> getWorkRemindMsgOfStu(IPage<NoteDTO> page, @Param("stuId") Integer stuId, @Param("klassId") Integer klassId);

    @Select("SELECT " +
            "    count(*) " +
            "FROM  " +
            "    note n " +
            "WHERE  " +
            "    n.type = '作业催促' " +
            "AND  " +
            "    ( " +
            "        n.id IN (SELECT note_id FROM student_note_receive WHERE student_id = #{stuId} )  " +
            "        OR  " +
            "        n.id IN (SELECT note_id FROM klass_note_receive WHERE klass_id = #{klassId} ) " +
            "    )")
    public Integer countWorkRemindMsgOfStu(@Param("stuId") Integer stuId, @Param("klassId") Integer klassId);
}
