package com.moyunzhijiao.system_backend.service.word;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyunzhijiao.system_backend.common.Constants;
import com.moyunzhijiao.system_backend.entiy.word.Font;
import com.moyunzhijiao.system_backend.exception.ServiceException;
import com.moyunzhijiao.system_backend.mapper.word.FontMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FontService extends ServiceImpl<FontMapper, Font> {

    @Autowired
    FontMapper fontMapper;
    public Integer getIdByField(String name){
        QueryWrapper<Font> queryWrapper = new QueryWrapper<>();
        System.out.println("到FontService里看看name是多少"+name);
        queryWrapper.select("id").eq("name",name);
        Font font = fontMapper.selectOne(queryWrapper);
        System.out.println("到FontService里看看font是多少"+font.toString());
        return font !=null ? font.getId():null;
    }

    public IPage<Font> selectPage(IPage<Font> page, String str) {
        QueryWrapper<Font> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name",str);
        return fontMapper.selectPage(page,queryWrapper);
    }

    public void addFont(String name) {
        Font font = new Font();
        font.setName(name);
        System.out.println("让1我们看下font的id是什么"+font.getId());
        System.out.println("让1我们看下font是什么"+font);
        fontMapper.insert(font);
        System.out.println("让2我们看下font的id是什么"+font.getId());
        System.out.println("让2我们看下font是什么"+font);
    }

    public void deleteFont(String id) {
        int rows = fontMapper.deleteById(id);
        if(rows!=1){
            throw new ServiceException(Constants.CODE_400,"删除参数错误！");
        }
    }
}
