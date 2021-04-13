package indi.snowmeow.zkoj.auth.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义jwt内容加强
 * @author snowmeow
 * @date 2021/4/13
 */
public class ZkojTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        Collection<GrantedAuthority> authorities = oAuth2Authentication.getAuthorities();
        if (authorities.size() > 0) {
            Map<String, Object> roleInfo = new HashMap<>();
            roleInfo.put("role", ((GrantedAuthority)authorities.toArray()[0]).getAuthority());
            ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(roleInfo);
        }
        return oAuth2AccessToken;
    }
}
