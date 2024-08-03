package com.moyunzhijiao.system_frontend.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "后端除文件外固定返回结果")
public class Result {
    @Schema(description = "运行结果")
    private String code;
    @Schema(description = "告诉前端错误的原因")
    private String msg;
    @Schema(description = "后台所携带的数据")
    private Object data;

    public static Result success(){//无参返回正确
        return new Result(Constants.CODE_200, "",null);
    }
    public static Result success(Object data){//有参返回正确
        return new Result(Constants.CODE_200, "",data);
    }
    public static Result error(String code,String msg){ //返回失败
        return new Result(code, msg,null);
    }
    public static Result error(){ //默认一个错误类
        return new Result(Constants.CODE_500,"系统错误",null);
    }
}