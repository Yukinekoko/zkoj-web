package indi.snowmeow.zkojweb.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import indi.snowmeow.zkojweb.model.po.UserPO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

/**
 * @author snowmeow
 * @date 2020/10/19
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Problem {
    /** 唯一标识符 */
    Long id;
    /** 标题 */
    String title;
    /** 题目描述 */
    String description;
    /** 样例输入 */
    String sampleInput;
    /** 样例输出 */
    String sampleOutput;
    /** 是否为特判题 */
    Boolean isSpj;
    /** 提示 */
    String hint;
    /** 创建日期 */
    Date createDate;
    /** 难度ID */
    Byte difficulty;
    /** 通过数量 */
    Integer accepted;
    /** 总提交数量 */
    Integer count;
    /** 算法标签列表 */
    List<ProblemTag> tag;
    /** 题目分类 */
    ProblemClass problemClass;
    /** 限制列表 */
    List<ProblemLimit> limit;
    /** 用户评测状态 */
    Byte status;
    /** 题目发布者*/
    UserPO userPO;
    /** 是否私有*/
    Boolean isPrivate;

    public Integer getAccepted() {
        return accepted;
    }

    public void setAccepted(Integer accepted) {
        this.accepted = accepted;
    }
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

    public Boolean isSpj() {
        return isSpj;
    }

    public void setSpj(Boolean spj) {
        isSpj = spj;
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



    public UserPO getUser() {
        return userPO;
    }

    public void setUser(UserPO userPO) {
        this.userPO = userPO;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<ProblemTag> getTag() {
        return tag;
    }

    public void setTag(List<ProblemTag> tag) {
        this.tag = tag;
    }

    public ProblemClass getProblemClass() {
        return problemClass;
    }

    public void setProblemClass(ProblemClass problemClass) {
        this.problemClass = problemClass;
    }

    public List<ProblemLimit> getLimit() {
        return limit;
    }

    public void setLimit(List<ProblemLimit> limit) {
        this.limit = limit;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Boolean getSpj() {
        return isSpj;
    }

    public Boolean getIsPrivate() {
        return isPrivate;
    }
    //获取通过率 问题查询接口调用
    public Double returnAcceptability(){
        if (this.count==0)
            return 0.0;
        BigDecimal count = new BigDecimal(this.count);
        BigDecimal accepted = new BigDecimal(this.accepted);
        BigDecimal a = accepted.divide(count,3, RoundingMode.HALF_DOWN);
        Double acceptability = a.multiply(new BigDecimal(100)).doubleValue();
        return acceptability;
    }
}
