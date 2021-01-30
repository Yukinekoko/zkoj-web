package indi.snowmeow.zkojweb.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/** 自定义JwtToken
 * @author snowmeow
 * @date 2020/10/13
 */
public class JwtToken implements AuthenticationToken {

    //jwtToken
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    /**
     * 类似获取用户名
     * */
    @Override
    public Object getPrincipal() {
        return token;
    }

    /**
     * 类似获取密码凭证
     * */
    @Override
    public Object getCredentials() {
        return token;
    }
}
