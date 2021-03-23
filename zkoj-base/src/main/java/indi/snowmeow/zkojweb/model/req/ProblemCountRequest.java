package indi.snowmeow.zkojweb.model.req;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author snowmeow
 * @date 2021/2/20
 */
public class ProblemCountRequest {

    /** 难度筛选 */
    @Min(1)
    @Max(3)
    private Byte difficulty;
    /** 分组筛选 */
    @Min(0)
    private Long classId;
    /** 标签筛选 */
    @Min(0)
    private Long tagId;

    @Override
    public String toString() {
        return "ProblemCountRequest{" +
                "difficulty=" + difficulty +
                ", classId=" + classId +
                ", tagId=" + tagId +
                '}';
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

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }
}
