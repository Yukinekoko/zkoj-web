package indi.snowmeow.zkoj.judger.model;

import java.io.Serializable;

/**
 * @author snowmeow
 * @date 2021/5/8
 */
public class RunningResult implements Serializable {

    /**
     * 状态码：
     * 0 - 正常结束
     * 1 - 未知异常
     * 2 - 超内存
     * 3 - 超时
     * 4 - 运行时异常
     * */
    private int code;

    /** 耗时 */
    private int time;

    /** 内存 */
    private int memory;

    /** 保留使用，运行信息 */
    private String message;

    private static final long serialVersionUID = 1L;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RunningResult{" +
                "code=" + code +
                ", time=" + time +
                ", memory=" + memory +
                ", message='" + message + '\'' +
                '}';
    }
}
