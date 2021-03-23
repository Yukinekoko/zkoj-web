package indi.snowmeow.zkojweb.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;

/**
 * 加密相关工具类
 * @author snowmeow
 * @date 2021/1/26
 */
public class EncodeUtil {

    /** 用户密码一次加密加盐值 */
    @Value("${zkoj.password-salt-1}")
    private static String PASSWORD_SALT_1;
    /** 用户密码二次加密加盐值 */
    @Value("${zkoj.password-salt-2}")
    private static String PASSWORD_SALT_2;

    /**
     * 对密码进行加密
     * @param password - 加密前的密码
     * @return 加密后的密码
     * */
    public static String encodePassword(String password) {
        password = DigestUtils.md5DigestAsHex((PASSWORD_SALT_1 + password.substring(0, 24)).getBytes());
        password = DigestUtils.md5DigestAsHex((PASSWORD_SALT_2 + password.toUpperCase().substring(8,32)).getBytes());
        password = password.toUpperCase();
        return password;
    }

}
