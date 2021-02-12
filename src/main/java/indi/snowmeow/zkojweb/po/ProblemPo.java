package indi.snowmeow.zkojweb.po;

import indi.snowmeow.zkojweb.model.ProblemClass;
import indi.snowmeow.zkojweb.model.ProblemLimit;
import indi.snowmeow.zkojweb.model.ProblemTag;
import indi.snowmeow.zkojweb.model.User;

import java.util.Date;
import java.util.List;

/**
 * @author snowmeow
 * @date 2021/2/12
 */
public class ProblemPo {

    /** 唯一标识符 */
    private Long id;
    /** 标题 */
    private String title;
    /** 题目描述 */
    private String description;
    /** 样例输入 */
    private String sampleInput;
    /** 样例输出 */
    private String sampleOutput;
    /** 是否为特判题 */
    private Boolean spj;
    /** 提示 */
    private String hint;
    /** 创建日期 */
    private Date createDate;
    /** 难度ID */
    private Byte difficulty;
    /** 分类ID */
    private Long classId;
    /** 发布人ID */
    private Long userId;
    /** 是否私有*/
    private Boolean privateProblem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSampleInput() {
        return sampleInput;
    }

    public void setSampleInput(String sampleInput) {
        this.sampleInput = sampleInput;
    }

    public String getSampleOutput() {
        return sampleOutput;
    }

    public void setSampleOutput(String sampleOutput) {
        this.sampleOutput = sampleOutput;
    }

    public Boolean getSpj() {
        return spj;
    }

    public void setSpj(Boolean spj) {
        this.spj = spj;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Byte getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Byte difficulty) {
        this.difficulty = difficulty;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getPrivateProblem() {
        return privateProblem;
    }

    public void setPrivateProblem(Boolean privateProblem) {
        this.privateProblem = privateProblem;
    }
}
