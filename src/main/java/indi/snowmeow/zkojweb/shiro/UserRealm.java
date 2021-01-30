package indi.snowmeow.zkojweb.shiro;

import indi.snowmeow.zkojweb.mapper.UserMapper;
import indi.snowmeow.zkojweb.model.User;
import indi.snowmeow.zkojweb.service.impl.UserServiceImpl;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户登录Realm
 * @author  snowmeow
 * @date    2020/10/13
 * */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserServiceImpl userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    /**
     * 授权逻辑
     * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行UserRealm授权逻辑");


        return null;
    }

    /**
     * 认证逻辑
     * */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String username = usernamePasswordToken.getUsername();
        String password = String.valueOf(usernamePasswordToken.getPassword());
        User user = userService.findByUserName(username);
        //用户不存在
        if(user == null) {
            throw new UnknownAccountException();
        }
        //密码错误
        if(!user.getPassword().equals(password)) {
            throw new IncorrectCredentialsException();
        }
        //用户被封禁
        if(user.getStatus() != 1) {
            throw new DisabledAccountException();
        }

        return new SimpleAuthenticationInfo(user, password, "");
    }
}
