package indi.snowmeow.zkoj.util.base;

import indi.snowmeow.zkoj.util.enums.ResultCodeEnum;

import java.io.Serializable;

/**
 * 统一返回JSON格式
 * @author snowmeow
 * @date 2021/3/23
 */
public class BaseResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int status;

    private String message;

    private T data;

    public BaseResult() {

    }

    public BaseResult(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public BaseResult(ResultCodeEnum resultCode, T data) {
        this.status = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    public static BaseResult<Void> success() {
        return success(null);
    }

    public static <U> BaseResult<U> success(U data) {
        return new BaseResult<>(ResultCodeEnum.SUCCESS, data);
    }

    public static BaseResult<Void> fail(ResultCodeEnum resultCode) {
        return new BaseResult<>(resultCode, null);
    }

    public static BaseResult<Void> fail() {
        return new BaseResult<>(ResultCodeEnum.COMMON_ERROR, null);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
