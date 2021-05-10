package indi.snowmeow.zkoj.judger.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author snowmeow
 * @date 2021/5/8
 */
@Data
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

}
