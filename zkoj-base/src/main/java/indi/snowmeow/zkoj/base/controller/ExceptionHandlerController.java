package indi.snowmeow.zkoj.base.controller;

import indi.snowmeow.zkoj.base.common.base.BaseException;
import indi.snowmeow.zkoj.base.common.base.BaseResult;
import indi.snowmeow.zkoj.base.common.enums.ResultCodeEnum;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.lang.reflect.Method;

/**
 * @author snowmeow
 * @date 2020/10/29
 */
@ResponseBody
@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(BaseException.class)
    public BaseResult<Void> handleBaseException(BaseException e) {
        return BaseResult.fail(e.getCode());
    }

    @ExceptionHandler({BindException.class,
            MethodArgumentNotValidException.class,
            MissingServletRequestParameterException.class})
    public BaseResult<Void> handleBindException(Exception e) {
        return BaseResult.fail(ResultCodeEnum.PARAM_ERROR);
    }

}
