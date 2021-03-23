package indi.snowmeow.zkojweb.exception;

/**
 * 参数出错异常
 * @author snowmeow
 * @date 2021/1/8
 */
public class ParamErrorException extends RuntimeException {

    public ParamErrorException(){
        super();
    }

    public ParamErrorException(String message) {
        super(message);
    }
}
