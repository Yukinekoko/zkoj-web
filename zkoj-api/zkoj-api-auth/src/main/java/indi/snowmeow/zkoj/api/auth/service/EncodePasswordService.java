package indi.snowmeow.zkoj.api.auth.service;

/**
 * @author snowmeow
 * @date 2021/4/20
 */
public interface EncodePasswordService {

    /**
     * 加密密码
     * @param password 前端请求的密码原文
     * @return 经过加密后的密码
     * */
    String encodePassword(String password);
}
