package com.moyunzhijiao.system_frontend.mapper.homework;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_frontend.controller.dto.homework.HomeworkSubmissionDTO;
import com.moyunzhijiao.system_frontend.controller.dto.StudentDTO;
import com.moyunzhijiao.system_frontend.entity.homework.HomeworkSubmission;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface HomeworkSubmissionMapper extends BaseMapper<HomeworkSubmission> {
    @Select("select homework.id as id ,homework.deadline as deadline ,homework.name as name " +
            "from homework_submission " +
            "   left join homework on homework_submission.homework_id = homework.id " +
            "   where homework_submission.student_id = #{stuId} and state = 0 ")
    public IPage<HomeworkSubmissionDTO> selectUnSubmitedPage(IPage<HomeworkSubmissionDTO> homeworkIPage, @Param("stuId")Integer stuId);

    @Select("select count(*) " +
            "   from homework_submission " +
            "       left join homework on homework_submission.homework_id = homework.id " +
            "where homework_submission.student_id = #{stuId} and state = 0 ")
    Integer countUnSubmitedPage(@Param("stuId") Integer stuId);

    @Select("SELECT homework_submission.id AS id, homework.name AS name, " +
            "   homework_submission.submited_time AS submitTime, " +
            "   CAST((homework_submission.system_score + homework_submission.teacher_score) AS DECIMAL(10, 2)) / 2 AS score " +
            "FROM homework_submission " +
            "   left join homework on homework_submission.homework_id = homework.id " +
            "   WHERE homework_submission.student_id = #{stuId} AND homework_submission.state = 1")
    IPage<HomeworkSubmissionDTO> selectSubmitedPage(IPage<HomeworkSubmissionDTO> iPage, @Param("stuId")Integer stuId);

    @Select("select count(*) " +
            "   from homework_submission " +
            "where homework_submission.student_id = #{stuId} and homework_submission.state = 1 ")
    Integer countSubmitedPage(@Param("stuId")Integer stuId);

    @Select("select student.name as name,student.id as stuId" +
            "   ,CAST((homework_submission.system_score + homework_submission.teacher_score) AS DECIMAL(10, 2)) / 2 AS score " +
            "   from homework_submission " +
            "       left join student on homework_submission.student_id = student.id " +
            "where homework_submission.homework_id = #{homeworkId}")
    IPage<StudentDTO> selectStudentPage(IPage<StudentDTO> page, @Param("homeworkId") Integer homeworkId);

    /*
     * 获取一个作业的是否完成的人的列表
     * @param: state 为作业是否完成的状态
     * */
    @Select({"<script>",
            "select s.id,s.name,hs.id as submissionId " ,
            "   from homework_submission hs " ,
            "       join student s on hs.student_id = s.id " ,
            "where hs.homework_id = #{homeworkId} " ,
            "<if test = 'reviewed'>" ,
            "   and hs.reviewed != 0 " ,
            "</if>" ,
            "<if test = '!reviewed'>" ,
            "   and hs.reviewed != 2 " ,
            "</if>",
            "<if test='klassId != null'>" ,
            "   and s.klass_id = #{klassId} " ,
            "</if>",
            "</script>"})
    List<StudentDTO> selectStudentOfHomework(@Param("homeworkId") Integer homeworkId, @Param("klassId") Integer klass,@Param("reviewed") Boolean reviewed);

}
