package indi.snowmeow.zkojweb.controller;

import com.auth0.jwt.exceptions.TokenExpiredException;
import indi.snowmeow.zkojweb.util.BaseBody;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * @author snowmeow
 * @date 2020/10/29
 */
@ControllerAdvice
public class ExceptionHandlerController {


    @ResponseBody
    @ExceptionHandler(TokenExpiredException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public Object handleTokenExpiredException(Exception e) {
        System.out.println("jwt拦截");
        return null;
    }

    @ResponseBody
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public Object handleAuthenticationException(Exception e) {
        System.out.println("au拦截");
        return null;
    }

    /**
     * 请求参数类型错误
     * */
    @ResponseBody
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Object handleMethodArgumentTypeMismatchException(Exception e) {
        return BaseBody.fail("参数错误");

    }


}
