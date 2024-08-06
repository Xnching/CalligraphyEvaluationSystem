package com.moyunzhijiao.system_frontend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_frontend.controller.dto.HomeworkSubmissionDTO;
import com.moyunzhijiao.system_frontend.entity.Homework;
import com.moyunzhijiao.system_frontend.entity.HomeworkSubmission;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface HomeworkSubmissionMapper extends BaseMapper<HomeworkSubmission> {
    @Select("select homework.id as id ,homework.deadline as deadline ,homework.name as name " +
            "from homework_submission left join homework on homework_submission.homework_id = homework.id " +
            "where homework_submission.student_id = #{stuId} and state = 0 ")
    public IPage<HomeworkSubmissionDTO> selectUnSubmitedPage(IPage<HomeworkSubmissionDTO> homeworkIPage, @Param("stuId")Integer stuId);

    @Select("select count(*) " +
            "from homework_submission left join homework on homework_submission.homework_id = homework.id " +
            "where homework_submission.student_id = #{stuId} and state = 0 ")
    Integer countUnSubmitedPage(@Param("stuId") Integer stuId);

    @Select("SELECT homework_submission.id AS id, homework_submission.name AS name, " +
            "homework_submission.created_time AS submitTime, " +
            "CAST((homework_submission.system_score + homework_submission.teacher_score) AS DECIMAL(10, 2)) / 2 AS score " +
            "FROM homework_submission " +
            "WHERE homework_submission.student_id = #{stuId} AND homework_submission.state = 1")
    IPage<HomeworkSubmissionDTO> selectSubmitedPage(IPage<HomeworkSubmissionDTO> iPage, @Param("stuId")Integer stuId);

    @Select("select count(*) " +
            " from homework_submission " +
            "where homework_submission.student_id = #{stuId} and homework_submission.state = 1 ")
    Integer countSubmitedPage(@Param("stuId")Integer stuId);
}
