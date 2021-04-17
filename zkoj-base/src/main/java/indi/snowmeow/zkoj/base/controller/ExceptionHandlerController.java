package indi.snowmeow.zkoj.base.controller;

import indi.snowmeow.zkoj.base.common.base.BaseException;
import indi.snowmeow.zkoj.base.common.base.BaseResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.lang.reflect.Method;

/**
 * @author snowmeow
 * @date 2020/10/29
 */
@ControllerAdvice
public class ExceptionHandlerController {


    @ResponseBody
    @ExceptionHandler(BaseException.class)
    public BaseResult<Void> handleBaseException(BaseException e) {
        return BaseResult.fail(e.getCode());
    }

}
