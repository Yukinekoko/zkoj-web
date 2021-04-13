package indi.snowmeow.zkoj.auth.service;

import indi.snowmeow.zkoj.auth.model.dto.UserLoginDTO;

/**
 * @author snowmeow
 * @date 2021/4/13
 */
public interface UserService {

    UserLoginDTO findByUsername(String username);
}
