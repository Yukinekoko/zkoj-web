package indi.snowmeow.zkoj.base.common.util;

import indi.snowmeow.zkoj.base.common.base.BaseException;
import indi.snowmeow.zkoj.base.common.enums.ResultCodeEnum;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author snowmeow
 * @date 2021/4/17
 */
public class AuthenticationUtil {

    private final static String AUTHENTICATION_PARAM = "Authorization";

    private final static String TOKEN_PREFIX = "Bearer ";

    public static boolean isLogin() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (!(requestAttributes instanceof ServletRequestAttributes)) {
            throw new BaseException(ResultCodeEnum.SYSTEM_ERROR, "登陆状态获取异常");
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        String token = request.getHeader(AUTHENTICATION_PARAM);
        if (StringUtils.isEmpty(token) || !token.startsWith(TOKEN_PREFIX)) {
            return false;
        }
        return true;
    }

    public static String getToken() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (!(requestAttributes instanceof ServletRequestAttributes)) {
            throw new BaseException(ResultCodeEnum.SYSTEM_ERROR, "登陆状态获取异常");
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        String token = request.getHeader(AUTHENTICATION_PARAM);
        if (StringUtils.isEmpty(token) || !token.startsWith(TOKEN_PREFIX)) {
            return null;
        }
        return token.substring(TOKEN_PREFIX.length());
    }
}
