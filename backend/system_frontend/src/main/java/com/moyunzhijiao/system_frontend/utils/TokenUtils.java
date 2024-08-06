package com.moyunzhijiao.system_frontend.utils;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

/**
 * 生成token
 * @return
 */
public class TokenUtils {
    /*
     * 生成Token
     */
    public static String genToken(String userId, String userType,String sign){
        System.out.println("生成token里的password是什么"+sign);
        System.out.println("生成token里的userId是什么"+userId);
        System.out.println("生成token里的userType是什么"+userType);
        return JWT.create().withAudience(userId)    // 将 user id 保存到 token 里面,作为载荷
                .withClaim("userType", userType)  // 将用户类型保存到 token 里面,作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(),24))   //24小时后token过期
                .sign(Algorithm.HMAC256(sign));     // 以 password 作为 token 的密钥
    }

    /*
     * 检查token
     * */
    public static String checkToken(String token,String userId, String userType,String sign) {
        if(StrUtil.isBlank(token)){
            return genToken(userId, userType,sign);
        }
        else {
            try {
                // 解码令牌
                DecodedJWT jwt = JWT.decode(token);
                // 获取令牌的过期时间
                Date expiresAt = jwt.getExpiresAt();
                // 如果令牌已经过期，生成新的令牌
                if (expiresAt.before(new Date())) {
                    return genToken(userId, userType,sign);
                }
            } catch (JWTDecodeException exception){
                // 如果令牌无效（例如，因为它被篡改），生成新的令牌
                return genToken(userId, userType,sign);
            }
            // 如果令牌没有过期，返回原令牌
            return token;
        }
    }

    public static void main(String[] args) {
        String token = genToken("12345", "admin", "secret");
        DecodedJWT jwt = JWT.decode(token);
        String userId = jwt.getAudience().get(0);
        String userType = jwt.getClaim("userType").asString();
        System.out.println("Token中的userId: " + userId);
        System.out.println("Token中的userType: " + userType);
    }
}