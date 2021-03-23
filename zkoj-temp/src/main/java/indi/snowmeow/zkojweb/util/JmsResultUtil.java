package indi.snowmeow.zkojweb.util;

import indi.snowmeow.zkojweb.messenger.JmsResult;
import indi.snowmeow.zkojweb.model.RequestSolution;

/**
 * 帮助构建JmsResult的工具类
 *
 * @author snowmeow
 * @date 2020/11/10
 */
public class JmsResultUtil {

    /** 创建新评测 */
    public static final int TYPE_SOLUTION_CREATE = 1;


    /**
     * 返回自定义的JmsResult类型
     * @param type 消息类型
     * @param data 消息内容
     * @param <T> 消息对象类型
     * @return 自定义的JmsResult类型
     * */
    public static <T> JmsResult<T> getJmsResult(int type, T data) {
        return new JmsResult<T>(type, data);
    }

    /**
     * 创建新的评测请求
     * @param data 评测信息
     * @return 封装好的评测请求
     * */
    public static JmsResult<RequestSolution> createSolution(RequestSolution data) {
        return new JmsResult<RequestSolution>(TYPE_SOLUTION_CREATE, data);
    }

}
