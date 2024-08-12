package com.moyunzhijiao.system_backend.mapper.outstanding;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_backend.controller.dto.outstanding.UnreviewedOutstandingDTO;
import com.moyunzhijiao.system_backend.entiy.outstanding.UnreviewedOutstanding;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UnreviewedOutstandingMapper extends BaseMapper<UnreviewedOutstanding> {

    @Select("select homework_submission.id as submissionId, homework.name as name, teacher.name as teacher, homework.type as type, student.name as author,grade.name as grade " +
            "from unreviewed_outstanding " +
            "left join teacher on unreviewed_outstanding.recommender_id = teacher.id " +
            "left join homework_submission on unreviewed_outstanding.submissions_id = homework_submission.id " +
            "left join student on homework_submission.student_id = student.id " +
            "left join grade on student.grade_id = grade.id " +
            "left join homework on homework_submission.homework_id = homework.id " +
            "where homework.name LIKE CONCAT('%',#{str},'%') " +
            "or teacher.name LIKE CONCAT('%',#{str},'%') " +
            "or student.name LIKE CONCAT('%',#{str},'%')")
    IPage<UnreviewedOutstandingDTO> page(IPage<UnreviewedOutstandingDTO> page, @Param("str") String str);
}
