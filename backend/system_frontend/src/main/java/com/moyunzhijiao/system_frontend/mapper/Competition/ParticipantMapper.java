package com.moyunzhijiao.system_frontend.mapper.Competition;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moyunzhijiao.system_frontend.controller.dto.StudentDTO;
import com.moyunzhijiao.system_frontend.entity.competition.Participant;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ParticipantMapper extends BaseMapper<Participant> {
    @Select({"<script>",
            "select s.id, s.name, k.name as klass,s.gender as gender ,g.name as grade , s.student_number as stuno ,",
            "   case ",
            "       when fr.submission_id is null then '未得奖' ",
            "       else fr.level ",
            "   end as level ",
            "from participant p ",
            "   left join student s on p.student_id = s.id ",
            "   left join final_rank fr on p.submission_id = fr.submission_id ",
            "   left join klass k on k.id = s.klass_id " +
            "   left join grade g on g.id = s.grade_id ",
            "where p.competition_id = #{competitionId} ",
            "   and p.student_id in ",
            "       <foreach item='id' collection='ids' open='(' separator=',' close=')'>",
            "           #{id}",
            "       </foreach>",
            "</script>"})
    List<StudentDTO> selectFinalRankOfStudent(@Param("ids") List<Integer> stuIdList,@Param("competitionId") Integer competitionId);
}
