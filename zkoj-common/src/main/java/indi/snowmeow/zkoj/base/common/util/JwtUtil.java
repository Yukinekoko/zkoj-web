package indi.snowmeow.zkoj.base.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import indi.snowmeow.zkoj.base.common.base.BaseException;
import indi.snowmeow.zkoj.base.common.enums.ResultCodeEnum;

/**
 * JWT TOKEN工具类
 * @author snowmeow
 * @date 2021/4/17
 */
public class JwtUtil {

    public static long getUserId(String token) {
        if (StringUtil.isEmpty(token)) {
            throw new BaseException(ResultCodeEnum.SYSTEM_ERROR, "token为空");
        }
        DecodedJWT jwt = decode(token);
        return jwt.getClaim("uid").asLong();
    }

    public static String getRole(String token) {
        if (StringUtil.isEmpty(token)) {
            throw new BaseException(ResultCodeEnum.SYSTEM_ERROR, "token为空");
        }
        DecodedJWT jwt = decode(token);
        return jwt.getClaim("role").asString();
    }

    public static DecodedJWT decode(String token) {
        if (StringUtil.isEmpty(token)) {
            throw new BaseException(ResultCodeEnum.SYSTEM_ERROR, "token为空");
        }
        try {
            return JWT.decode(token);
        } catch (JWTDecodeException exception) {
            throw new BaseException(ResultCodeEnum.TOKEN_ERROR);
        }
    }
}
