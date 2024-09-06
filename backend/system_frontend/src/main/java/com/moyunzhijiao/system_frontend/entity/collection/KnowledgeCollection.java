package com.moyunzhijiao.system_frontend.entity.collection;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value="knowledge_collection")
public class KnowledgeCollection {
    Integer studentId;
    Integer resourcesId;

    public KnowledgeCollection(Integer stuId, Integer kId) {
        this.studentId=stuId;
        this.resourcesId=kId;
    }
}