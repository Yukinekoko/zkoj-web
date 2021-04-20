package indi.snowmeow.zkoj.auth.service.impl;

import indi.snowmeow.zkoj.api.auth.service.EncodePasswordService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author snowmeow
 * @date 2021/4/20
 */
@DubboService(protocol = "dubbo", version = "1.0.0")
public class EncodePasswordServiceImpl implements EncodePasswordService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
