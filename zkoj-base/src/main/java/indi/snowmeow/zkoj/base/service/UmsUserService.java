package indi.snowmeow.zkoj.base.service;

import indi.snowmeow.zkoj.base.model.dto.UserInfoUpdateDTO;
import indi.snowmeow.zkoj.base.model.dto.UserRegisterDTO;
import indi.snowmeow.zkoj.base.model.entity.UmsUser;

/**
 * @author snowmeow
 * @date 2021/4/17
 */
public interface UmsUserService {

    UmsUser find(long id);
    /**
     * 根据用户名获取用户信息
     * */
    UmsUser findFromUsername(String username);

    /**
     * 更新用户基本信息
     * */
    void updateBaseInfo(UserInfoUpdateDTO requestDTO);

    /**
     * 更新密码
     * */
    void updatePassword(String oldPassword, String newPassword);

    /**
     * 新增用户
     * */
    long insert(UserRegisterDTO requestDTO);
}
