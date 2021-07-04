package indi.snowmeow.zkoj.auth.aspect;

import indi.snowmeow.zkoj.base.common.base.BaseResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

/**
 * 对登录请求返回值的切面处理
 * @author snowmeow
 * @date 2021/7/3
 */
@Component
@Aspect
public class PostTokenAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostTokenAspect.class);

    @Around("execution(* org.springframework.security.oauth2.provider.endpoint.TokenEndpoint.postAccessToken(..))")
    public Object handleTokenResult(ProceedingJoinPoint joinPoint) throws Throwable {

        Object sourceMethodResult = joinPoint.proceed();
        ResponseEntity<?> sourceResponseEntity = (ResponseEntity<?>) sourceMethodResult;
        OAuth2AccessToken oAuth2AccessToken = (OAuth2AccessToken) sourceResponseEntity.getBody();

        if (sourceResponseEntity.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(HttpStatus.OK).body(BaseResult.success(oAuth2AccessToken));
        } else {
            LOGGER.warn("意料之外的流程");
            return sourceMethodResult;
        }
    }

}
