package indi.snowmeow.zkoj.base.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Date;
import java.util.List;

/**
 * @author snowmeow
 * @date 2021/2/12
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProblemDetailVO {

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
    /** 提示 */
    private String hint;
    /** 创建日期 */
    private Date createDate;
    /** 难度ID */
    private Byte difficulty;
    /** 总提交数 */
    private Integer count;
    /** 总AC数 */
    private Integer accepted;
    /** 标签 */
    private List<ProblemTagVO> tag;
    /** 分类 */
    private ProblemClassPreviewVO problemClass;
    /** 限制 */
    private List<CurrentProblemLimitVO> limit;

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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getAccepted() {
        return accepted;
    }

    public void setAccepted(Integer accepted) {
        this.accepted = accepted;
    }

    public List<ProblemTagVO> getTag() {
        return tag;
    }

    public void setTag(List<ProblemTagVO> tag) {
        this.tag = tag;
    }

    public ProblemClassPreviewVO getProblemClass() {
        return problemClass;
    }

    public void setProblemClass(ProblemClassPreviewVO problemClass) {
        this.problemClass = problemClass;
    }

    public List<CurrentProblemLimitVO> getLimit() {
        return limit;
    }

    public void setLimit(List<CurrentProblemLimitVO> limit) {
        this.limit = limit;
    }
}
