package indi.snowmeow.zkoj.util.enums;

/**
 * API代码
 * @author snowmeow
 * @date 2021/3/23
 */
public enum ResultCodeEnum {

    /** 正常返回 */
    SUCCESS(1, "Success"),

    /** 默认异常 */
    COMMON_ERROR(0, "Error"),

    /** 参数异常 */
    PARAM_ERROR(10001, "参数错误"),

    /** 用户异常 */
    USER_ERROR(20001, "用户异常"),
    USER_ACCOUNT_NOT_EXIST(20002, "账号不存在"),
    USER_NOT_LOGIN(20003, "用户未登录");

    private final int code;

    private final String message;

    ResultCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
