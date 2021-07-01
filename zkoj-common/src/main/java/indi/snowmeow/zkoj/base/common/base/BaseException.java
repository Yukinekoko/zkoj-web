package indi.snowmeow.zkoj.base.common.base;

import indi.snowmeow.zkoj.base.common.enums.ResultCodeEnum;

/**
 * @author snowmeow
 * @date 2021/3/23
 */
public class BaseException extends RuntimeException {

    private final ResultCodeEnum code;

    public BaseException() {
        this.code = ResultCodeEnum.COMMON_ERROR;
    }

    public BaseException(ResultCodeEnum code) {
        this.code = code;
    }

    public BaseException(String message) {
        this(ResultCodeEnum.COMMON_ERROR, message);
    }

    public BaseException(ResultCodeEnum code, String message) {
        super(message);
        this.code = ResultCodeEnum.COMMON_ERROR;
    }

    public ResultCodeEnum getCode() {
        return code;
    }
}
