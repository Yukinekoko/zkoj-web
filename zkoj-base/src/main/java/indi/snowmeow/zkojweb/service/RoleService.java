package indi.snowmeow.zkojweb.service;

import indi.snowmeow.zkojweb.model.po.RolePO;

/**
 * @author snowmeow
 * @date 2021/3/23
 */
public interface RoleService {

    /**
     * 获取指定用户的权限对象
     *
     * @param username 用户名
     * @return 权限对象
     */
    RolePO getFromUsername(String username);
}
