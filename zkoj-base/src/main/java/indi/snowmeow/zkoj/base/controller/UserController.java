package indi.snowmeow.zkoj.base.controller;

import indi.snowmeow.zkoj.base.common.base.BaseResult;
import indi.snowmeow.zkoj.base.service.UmsUserService;
import indi.snowmeow.zkoj.base.service.UserDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author snowmeow
 * @date 2021/4/17
 */
@RestController
public class UserController {

    @Autowired
    private UserDomainService userDomainService;

    /**
     * 获取用户信息接口
     * @param username
     * @return UserInfo
     */
    @GetMapping("/user/id/{username}")
    public Object getUserInfo (@PathVariable String username){
        return BaseResult.success(userDomainService.getInfoFromUsername(username));
    }
}
