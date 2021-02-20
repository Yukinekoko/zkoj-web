package indi.snowmeow.zkojweb.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

/**
 * 问题简略信息
 * @author snowmeow
 * @date 2021/2/14
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProblemPreviewVO {

    /** 唯一标识符 */
    private Long id;
    /** 标题 */
    private String title;
    /** 难度ID */
    private Byte difficulty;
    /** 总提交数 */
    private Integer count;
    /** 总AC数 */
    private Integer accepted;
    /** 用户做题状态 */
    private Byte status;
    /** 标签 */
    private List<ProblemTagVO> tag;
    /** 分类 */
    private ProblemClassPreviewVO problemClass;

    @Override
    public String toString() {
        return "ProblemPreviewVO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", difficulty=" + difficulty +
                ", count=" + count +
                ", accepted=" + accepted +
                ", status=" + status +
                ", tag=" + tag +
                ", problemClass=" + problemClass +
                '}';
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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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

}
