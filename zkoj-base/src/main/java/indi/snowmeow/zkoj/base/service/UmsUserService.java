package indi.snowmeow.zkoj.base.service;

import indi.snowmeow.zkoj.base.model.entity.UmsUser;

/**
 * @author snowmeow
 * @date 2021/4/17
 */
public interface UmsUserService {

    /**
     * 根据用户名获取用户信息
     * */
    UmsUser getFromUsername(String username);
}
