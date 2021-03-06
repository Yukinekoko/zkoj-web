package indi.snowmeow.zkojweb.shiro;

import indi.snowmeow.zkojweb.util.JwtUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * @author snowmeow
 * @date 2020/10/14
 */
public class JwtRealm extends AuthorizingRealm {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtRealm.class);

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        Subject subject = SecurityUtils.getSubject();
        String token = (String) subject.getPrincipal();
        String role = JwtUtil.getRole(token);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> roles = new HashSet<>();
        roles.add(role);
        info.setRoles(roles);

        return info;
    }


    /**
     * 验证token是否有效
     * */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        LOGGER.info("进入{}登录逻辑", JwtRealm.class.getName());
        String token = (String) authenticationToken.getCredentials();
        if(JwtUtil.verify(token)) {
            LOGGER.info("token认证成功！");
            return new SimpleAuthenticationInfo(token, token, "");
        }
        LOGGER.info("token认证失败！");
        throw new AuthenticationException("token认证失败！");
    }
}
