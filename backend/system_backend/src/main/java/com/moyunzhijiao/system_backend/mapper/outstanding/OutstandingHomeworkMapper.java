package com.moyunzhijiao.system_backend.mapper.outstanding;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_backend.controller.dto.outstanding.OutstandingHomeworkDTO;
import com.moyunzhijiao.system_backend.entiy.outstanding.OutstandingHomework;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface OutstandingHomeworkMapper extends BaseMapper<OutstandingHomework> {
    @Select("select homework_submission.id as submissionId, homework.name as name, teacher.name as teacher, homework.type as type, student.name as author,grade.name as grade,user.name as reviewer " +
            "from outstanding_homework " +
            "left join user on outstanding_homework.reviewer_id = user.id " +
            "left join teacher on outstanding_homework.recommender_id = teacher.id " +
            "left join homework_submission on outstanding_homework.submissions_id = homework_submission.id " +
            "left join student on homework_submission.student_id = student.id " +
            "left join grade on student.grade_id = grade.id " +
            "left join homework on homework_submission.homework_id = homework.id " +
            "where homework.name LIKE CONCAT('%',#{str},'%') " +
            "or teacher.name LIKE CONCAT('%',#{str},'%') " +
            "or student.name LIKE CONCAT('%',#{str},'%')")
    IPage<OutstandingHomeworkDTO> page(IPage<OutstandingHomeworkDTO> page, @Param("str") String str);
}
