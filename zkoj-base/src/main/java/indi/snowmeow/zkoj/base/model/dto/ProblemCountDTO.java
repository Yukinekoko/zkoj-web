package indi.snowmeow.zkoj.base.model.dto;

/**
 * @author snowmeow
 * @date 2021/2/20
 */
public class ProblemCountDTO {

    /** 难度筛选 */
    private Byte difficulty;
    /** 分组筛选 */
    private Long classId;
    /** 标签筛选 */
    private Long tagId;

    @Override
    public String toString() {
        return "ProblemCountDTO{" +
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
