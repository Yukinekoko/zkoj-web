package indi.snowmeow.zkojweb.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import indi.snowmeow.zkojweb.po.LanguagePO;

/**
 * 通过消息队列传递给判题机的评测信息对象
 * @author snowmeow
 * @date 2020/12/22
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RequestSolution {

    /** 评测ID */
    private Long id;
    /** 问题ID */
    private Long problemId;
    /** 代码 */
    private String sourceCode;
    /** 语言对象 */
    private LanguagePO language;
    /** 耗时限制 */
    private Integer time;
    /** 内存限制 */
    private Integer memory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProblemId() {
        return problemId;
    }

    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public LanguagePO getLanguage() {
        return language;
    }

    public void setLanguage(LanguagePO language) {
        this.language = language;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getMemory() {
        return memory;
    }

    public void setMemory(Integer memory) {
        this.memory = memory;
    }
}
