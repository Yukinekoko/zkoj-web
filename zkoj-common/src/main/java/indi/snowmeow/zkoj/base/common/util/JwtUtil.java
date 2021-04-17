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

    public static Long getUserId(String token) {
        DecodedJWT jwt = decode(token);
        return jwt.getClaim("uid").asLong();
    }

    public static String getRole(String token) {
        DecodedJWT jwt = decode(token);
        return jwt.getClaim("role").asString();
    }

    public static DecodedJWT decode(String token) {
        try {
            return JWT.decode(token);
        } catch (JWTDecodeException exception) {
            throw new BaseException(ResultCodeEnum.TOKEN_ERROR);
        }
    }
}
