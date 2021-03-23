package indi.snowmeow.zkojweb.controller;

import indi.snowmeow.zkojweb.util.BaseBody;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import javax.validation.ConstraintViolationException;

/**
 * @author snowmeow
 * @date 2020/10/29
 */
@ControllerAdvice
public class ExceptionHandlerController {

    /**
     * 请求参数类型错误
     * */
    @ResponseBody
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Object handleMethodArgumentTypeMismatchException(Exception e) {
        return BaseBody.fail("参数错误");

    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public Object handleConstraintViolationException(Exception e) {
        return BaseBody.fail("参数错误");
    }

    @ResponseBody
    @ExceptionHandler(BindException.class)
    public Object handleBindException(Exception e) {
        return BaseBody.fail("参数错误");
    }


}
