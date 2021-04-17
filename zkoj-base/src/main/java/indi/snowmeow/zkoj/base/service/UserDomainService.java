package indi.snowmeow.zkoj.base.service;

import indi.snowmeow.zkoj.base.model.vo.UserInfoVO;

/**
 * @author snowmeow
 * @date 2021/4/17
 */
public interface UserDomainService {

    /**
     * 根据username获取指定用户的个人信息
     * */
    UserInfoVO getInfoFromUsername(String username);
}
