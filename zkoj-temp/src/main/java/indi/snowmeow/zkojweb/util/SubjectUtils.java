package indi.snowmeow.zkojweb.util;

import indi.snowmeow.zkojweb.service.impl.UserServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author snowmeow
 * @date 2021/2/19
 */
public class SubjectUtils {

    /**
     * 获取当前请求的用户username
     * */
    public static String getUsername() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            String token = (String)subject.getPrincipal();
            return JwtUtil.getSubject(token);
        }
        return null;
    }

}
