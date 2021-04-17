package indi.snowmeow.zkoj.base.exception;

/**
 * @author snowmeow
 * @date 2021/2/21
 */
public class ApiException extends RuntimeException {

    public ApiException() {
        super();
    }

    public ApiException(String message) {
        super(message);
    }
}
