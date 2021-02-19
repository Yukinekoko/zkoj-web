package indi.snowmeow.zkojweb.util;

import java.util.Map;

/** responseBody模板
 * @author snowmeow
 * @date 2020/10/13
 */
public class BaseBody<T> {

    private int status;

    private String message;

    private T data;

    /** 返回数据类型为Map<String, Object>的请求成功response模板
     * @param data data内容；可为null
     * @return body内容
     * */
    public static BaseBody<Map<String, Object>> success(Map<String, Object> data) {
        return new BaseBody<Map<String, Object>>(1, "Success.", data);
    }

    /** 返回数据类型为空的请求成功response模板
     * @return body内容
     * */
    public static BaseBody<Void> success() {
        return success((Void)null);
    }

    public static <U> BaseBody<U> success(U data) {
        return new BaseBody<U>(1, "Success", data);
    }

    public static BaseBody<Void> fail(String message) {
        return new BaseBody<>(0, message, null);
    }

    public BaseBody() {
    }

    public BaseBody(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
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
