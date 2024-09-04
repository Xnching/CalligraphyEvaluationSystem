package com.moyunzhijiao.system_frontend.entity.competition;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value="csubmission_image")
public class CsubmissionImage {
    Integer submissionId;
    String pictureUrl;
}
