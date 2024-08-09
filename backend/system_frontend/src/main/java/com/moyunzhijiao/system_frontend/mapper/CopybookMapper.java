package com.moyunzhijiao.system_frontend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moyunzhijiao.system_frontend.entity.Copybook;
import org.apache.ibatis.annotations.Select;

public interface CopybookMapper extends BaseMapper<Copybook> {
    @Select("select copybook.* ,font.name as font " +
            "from copybook left join font on copybook.font_id = font.id ")
    IPage<Copybook> selectPageAndFont(IPage<Copybook> page);
}
