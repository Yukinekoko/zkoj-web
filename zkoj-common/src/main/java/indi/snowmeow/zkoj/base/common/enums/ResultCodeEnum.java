package indi.snowmeow.zkoj.base.common.enums;

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

    /** 系统异常 */
    PARAM_ERROR(10001, "参数错误"),
    TOKEN_ERROR(10002, "token异常"),
    SYSTEM_ERROR(10003, "系统异常"),
    SERVICE_NOT_AVAILABLE(10004, "服务暂不可用"),
    MQ_ERROR(10004, "消息总线异常"),
    /** 用户异常 */
    USER_ERROR(20001, "用户异常"),
    USER_ACCOUNT_NOT_EXIST(20002, "账号不存在"),
    USER_NOT_LOGIN(20003, "用户未登录"),
    USER_LOGIN_FAILURE(20004, "登录失败"),
    USER_PASSWORD_REPEAT(20005, "密码重复"),
    USER_PASSWORD_ERROR(20006, "密码错误"),
    USER_USERNAME_EXIST(20007, "用户名已存在"),

    /** zkoj-judger 判题服务异常 */
    JUDGER_ERROR(30001, "判题异常")
    ;

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
