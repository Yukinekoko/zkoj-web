package indi.snowmeow.zkojweb.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

/**
 * @author snowmeow
 * @date 2021/3/23
 */
public class SecurityPasswordEncoder implements PasswordEncoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityPasswordEncoder.class);

    /**
     * 用户密码一次加密加盐值
     */
    @Value("${zkoj.password-salt-1}")
    private static String PASSWORD_SALT_1;
    /**
     * 用户密码二次加密加盐值
     */
    @Value("${zkoj.password-salt-2}")
    private static String PASSWORD_SALT_2;

    @Override
    public String encode(CharSequence charSequence) {
        LOGGER.info("encode:" + charSequence);
        if (charSequence.length() != 32) {
            return charSequence.toString();
        }
        String encodeString = DigestUtils.md5DigestAsHex((PASSWORD_SALT_1 +
            charSequence.toString().substring(0,24)).getBytes());
        encodeString= DigestUtils.md5DigestAsHex((PASSWORD_SALT_2 +
            encodeString.toUpperCase().substring(8,32)).getBytes());
        return encodeString;
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        String encodeString = encode(charSequence);
        return encodeString.equals(s);
    }
}
