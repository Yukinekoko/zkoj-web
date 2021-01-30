package indi.snowmeow.zkojweb.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/** 评测测试点 对应check_point数据表
 * @author snowmeow
 * @date 2020/10/27
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CheckPoint {

    /** 唯一标识符 */
    long id;
    /** 问题ID */
    long problemId;
    /** 标准输入 */
    String input;
    /** 标准输出 */
    String output;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProblemId() {
        return problemId;
    }

    public void setProblemId(long problemId) {
        this.problemId = problemId;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
