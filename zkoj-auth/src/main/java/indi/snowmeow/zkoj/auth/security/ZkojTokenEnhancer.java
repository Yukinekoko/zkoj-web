package indi.snowmeow.zkoj.auth.security;

import indi.snowmeow.zkoj.auth.model.dto.UserLoginDTO;
import indi.snowmeow.zkoj.auth.model.entity.UmsUser;
import indi.snowmeow.zkoj.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Autowired
    UserService userService;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        Collection<GrantedAuthority> authorities = oAuth2Authentication.getAuthorities();
        Map<String, Object> content = new HashMap<>();
        if (authorities.size() > 0) {
            content.put("role", ((GrantedAuthority)authorities.toArray()[0]).getAuthority());
        }
        UserDetails userDetails = (UserDetails) oAuth2Authentication.getPrincipal();
        long id = userService.getIdFromUsername(userDetails.getUsername());
        content.put("uid", id);
        content.put("name", userService.findName(id));
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(content);
        return oAuth2AccessToken;
    }
}
