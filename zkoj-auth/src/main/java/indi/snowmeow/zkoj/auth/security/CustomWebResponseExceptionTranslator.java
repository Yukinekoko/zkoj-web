package indi.snowmeow.zkoj.auth.security;

import indi.snowmeow.zkoj.base.common.enums.ResultCodeEnum;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;

/**
 * 自定义转换器
 * 修改用户名或密码错误时返回的内容
 * @author snowmeow
 * @date 2021/7/4
 */
public class CustomWebResponseExceptionTranslator extends DefaultWebResponseExceptionTranslator {

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {

        /*
        * 当用户名或密码错误时在DaoAuthenticationProvider中会抛出BadCredentialsException异常，
        * 而在后续处理中会被捕获并转换为抛出InvalidGrantException异常
        * */
        if (e instanceof InvalidGrantException) {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Cache-Control", "no-store");
            headers.set("Pragma", "no-cache");
            ResultCodeEnum resultCode = ResultCodeEnum.USER_LOGIN_FAILURE;
            OAuth2Exception oAuth2Exception = new OAuth2Exception(resultCode.getMessage());
            oAuth2Exception.addAdditionalInformation("status", String.valueOf(resultCode.getCode()));
            return new ResponseEntity<>(oAuth2Exception, headers,
                    HttpStatus.OK);
        }
        return super.translate(e);
    }
}
