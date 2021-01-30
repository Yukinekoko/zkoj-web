package indi.snowmeow.zkojweb.messenger;

import javax.xml.crypto.Data;

/**
 * 封装好jms用的数据对象格式
 *
 * @author snowmeow
 * @date 2020/11/10
 */
public class JmsResult<T> {

    /** 消息类型 */
    private int type;
    /** 消息数据 */
    private T data;

    public JmsResult() {
    }

    public JmsResult(int type, T data) {
        this.type = type;
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
