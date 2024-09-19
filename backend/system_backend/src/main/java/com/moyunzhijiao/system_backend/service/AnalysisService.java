package com.moyunzhijiao.system_backend.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moyunzhijiao.system_backend.controller.dto.AnalysisDTO;
import com.moyunzhijiao.system_backend.controller.dto.base.GradeDTO;
import com.moyunzhijiao.system_backend.controller.dto.base.RegionDTO;
import com.moyunzhijiao.system_backend.entiy.base.Grade;
import com.moyunzhijiao.system_backend.entiy.base.Region;
import com.moyunzhijiao.system_backend.entiy.base.School;
import com.moyunzhijiao.system_backend.entiy.front.Student;
import com.moyunzhijiao.system_backend.entiy.front.Teacher;
import com.moyunzhijiao.system_backend.entiy.resource.Video;
import com.moyunzhijiao.system_backend.entiy.word.Copybook;
import com.moyunzhijiao.system_backend.entiy.word.Font;
import com.moyunzhijiao.system_backend.entiy.word.SystemTemplate;
import com.moyunzhijiao.system_backend.mapper.base.RegionMapper;
import com.moyunzhijiao.system_backend.mapper.base.SchoolMapper;
import com.moyunzhijiao.system_backend.mapper.front.StudentMapper;
import com.moyunzhijiao.system_backend.mapper.front.TeacherMapper;
import com.moyunzhijiao.system_backend.mapper.resource.VideoMapper;
import com.moyunzhijiao.system_backend.service.base.GradeService;
import com.moyunzhijiao.system_backend.service.word.CopybookService;
import com.moyunzhijiao.system_backend.service.word.FontService;
import com.moyunzhijiao.system_backend.service.word.SystemTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalysisService {
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    VideoMapper videoMapper;
    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    SchoolMapper schoolMapper;
    @Autowired
    RegionMapper regionMapper;
    @Autowired
    CopybookService copybookService;
    @Autowired
    SystemTemplateService systemTemplateService;
    @Autowired
    GradeService gradeService;
    @Autowired
    FontService fontService;
    /*
     * 获取本日新增的数量
     * */
    public Integer getSingleAddData(String type,String target) {
        Integer number = null;
        if (type.equals("day"))
            number = studentMapper.countSingleAddDataOfDay(target);
        else if (type.equals("month"))
            number = studentMapper.countSingleAddDataOfMonth(target);
        return number;
    }

    /*
     * 统计视频中的各个类型的量
     * */
    public List<AnalysisDTO> getTypeCount(String target) {
        return videoMapper.selectTypeCount(target);
    }

    /*
    * 获取每个省级地区的学校数、教师人数，学生人数
    * */
    public List<RegionDTO> getProvinceStatistics() {
        List<RegionDTO> result = new ArrayList<>();
        Map<Integer,RegionDTO> map = new HashMap<>();
        // 获取所有省份
        List<Region> provinces = regionMapper.selectList(new QueryWrapper<Region>().eq("level", 1));


        // 初始化统计结果
        for (Region region : provinces) {
            RegionDTO regionDTO = new RegionDTO();
            BeanUtil.copyProperties(region,regionDTO);
            regionDTO.setSchoolNumber(0);
            regionDTO.setTeacherNumber(0);
            regionDTO.setStudentNumber(0);
            result.add(regionDTO);
            map.put(regionDTO.getId(),regionDTO);
        }


        // 分批次处理学校数据
        int batchSize = 10000;
        int schoolOffset = 0;
        while (true) {
            List<School> schools = schoolMapper.selectList(new QueryWrapper<School>().last("LIMIT " + schoolOffset + ", " + batchSize));
            if (schools.isEmpty()) break;
            for (School school : schools) {
                RegionDTO regionDTO = map.get(school.getMaxRegionId());
                regionDTO.setStudentNumber(regionDTO.getStudentNumber()+school.getStudentCount());
                regionDTO.setTeacherNumber(regionDTO.getTeacherNumber()+school.getTeacherCount());
                regionDTO.setSchoolNumber(regionDTO.getSchoolNumber()+1);
            }
            schoolOffset += batchSize;
        }

        return result;
    }

    /*
    * 获取关于一个年级或一个字体的系统模板数量和字帖数量
    * */
    public Map<String, List<?>> getFontAndGradeData() {
        Map<String, List<?>> result = new HashMap<>();
        List<GradeDTO> fonts = new ArrayList<>();
        List<GradeDTO> grades = new ArrayList<>();
        Map<Integer, GradeDTO> fontIds = new HashMap<>();
        Map<Integer, GradeDTO> gradeIds = new HashMap<>();

        // 获取所有主体数据，也就是有哪些年级和字体
        List<Grade> gradeList = gradeService.list();
        List<Font> fontList = fontService.list();

        // 初始化统计结果
        initializeData(fontList, fonts, fontIds, Font.class);
        initializeData(gradeList, grades, gradeIds, Grade.class);

        // 获取所有待分析的数据
        List<Copybook> copybookList = copybookService.list();
        List<SystemTemplate> systemTemplateList = systemTemplateService.list();

        //统计分析数据
        updateCopybookData(copybookList, gradeIds, fontIds);
        updateSystemTemplateData(systemTemplateList, fontIds);

        result.put("fontData", fonts);
        result.put("gradeData", grades);
        return result;
    }

    // 使用反射和泛型初始化统计结果
    private <T> void initializeData(List<T> sourceList, List<GradeDTO> targetList, Map<Integer, GradeDTO> idMap, Class<T> clazz) {
        for (T source : sourceList) {
            GradeDTO gradeDTO = new GradeDTO();
            BeanUtil.copyProperties(source, gradeDTO);
            gradeDTO.setCopybookNumber(0);
            gradeDTO.setTemplateNumber(0);

            try {
                Method getIdMethod = clazz.getMethod("getId");
                Integer id = (Integer) getIdMethod.invoke(source);
                idMap.put(id, gradeDTO);
            } catch (Exception e) {
                e.printStackTrace();
            }

            targetList.add(gradeDTO);
        }
    }

    private void updateCopybookData(List<Copybook> copybookList, Map<Integer, GradeDTO> gradeIds, Map<Integer, GradeDTO> fontIds) {
        for (Copybook copybook : copybookList) {
            GradeDTO gradeDTO1 = gradeIds.get(copybook.getGradeId());
            GradeDTO gradeDTO2 = fontIds.get(copybook.getFontId());

            if (gradeDTO1 != null) {
                gradeDTO1.setCopybookNumber(gradeDTO1.getCopybookNumber() + 1);
            }

            if (gradeDTO2 != null) {
                gradeDTO2.setCopybookNumber(gradeDTO2.getCopybookNumber() + 1);
            }
        }
    }

    private void updateSystemTemplateData(List<SystemTemplate> systemTemplateList, Map<Integer, GradeDTO> fontIds) {
        for (SystemTemplate systemTemplate : systemTemplateList) {
            GradeDTO gradeDTO = fontIds.get(systemTemplate.getFontId());

            if (gradeDTO != null) {
                gradeDTO.setTemplateNumber(gradeDTO.getTemplateNumber() + 1);
            }
        }
    }

}
