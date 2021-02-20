package indi.snowmeow.zkojweb.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import indi.snowmeow.zkojweb.po.LanguagePO;

import java.util.Date;

/**
 * Solution对象
 * @author snowmeow
 * @date 2020/10/20
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Solution {

    /** 唯一标识符 */
    private Long id;
    /** 评测用时 */
    private Integer time;
    /** 使用内存 */
    private Integer memory;
    /** 提交时间 */
    private Date submitDate;
    /** 评测状态ID */
    private Byte statusId;
    /** 语言 */
    private LanguagePO language;
    /** 关联问题 */
    private Problem problem;
    /** 提交用户 */
    private User user;



    /** 状态 */
    private Status status;
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public LanguagePO getLanguage() {
        return language;
    }

    public void setLanguage(LanguagePO language) {
        this.language = language;
    }
}
