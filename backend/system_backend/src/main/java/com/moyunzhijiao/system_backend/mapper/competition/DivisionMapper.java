package com.moyunzhijiao.system_backend.mapper.competition;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moyunzhijiao.system_backend.entiy.competition.Division;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

public interface DivisionMapper extends BaseMapper<Division> {
    @Select("<script>" +
            "SELECT d.*, " +
            "c.name AS competitionName, " +
            "COUNT(DISTINCT r.teacher_id) AS teacherNumber, " +
            "COUNT(DISTINCT cs.id) AS submissionNumber, " +
            "COUNT(DISTINCT CASE WHEN cs.initial_score != -1 THEN cs.id END) AS reviewedNumber " +
            "   FROM division d " +
            "   LEFT JOIN competition c ON d.competition_id = c.id " +
            "   LEFT JOIN reviewers r ON d.id = r.division_id " +
            "   LEFT JOIN competition_submissions cs ON d.id = cs.division_id " +
            "   LEFT JOIN participant p ON d.id = p.division_id " +
            "WHERE c.state NOT IN ('准备报名中', '已结束') " +
            "   AND d.delete_flag = 0 " +
            "   AND c.delete_flag = 0 " +
            "GROUP BY d.id " +
            "</script>")
    List<Division> selectIng();
}
