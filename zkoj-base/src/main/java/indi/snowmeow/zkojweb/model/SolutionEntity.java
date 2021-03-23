package indi.snowmeow.zkojweb.model;

import java.util.Date;

/**
 * solution表对应的实体类
 * @author snowmeow
 * @date 2020/11/11
 */
public class SolutionEntity {

    /** 唯一标识符 */
    private Long id;
    /** 问题ID */
    private Long problemId;
    /** 用户ID */
    private Long userId;
    /** 评测用时 */
    private Integer time;
    /** 使用内存 */
    private Integer memory;
    /** 提交时间 */
    private Date submitDate;
    /** 评测状态ID */
    private Byte statusId;
    /** 语言ID */
    private Long languageId;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public Byte getStatusId() {
        return statusId;
    }

    public void setStatusId(Byte statusId) {
        this.statusId = statusId;
    }

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Long languageId) {
        this.languageId = languageId;
    }
}
