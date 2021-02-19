package indi.snowmeow.zkojweb.po;

/**
 * @author snowmeow
 * @date 2021/2/12
 */
public class ProblemLimitPO {

    /** 唯一标识符 */
    private Long id;
    /** 所属题目ID */
    private Long problemId;
    /** 语言ID */
    private Long languageId;
    /** 限制时间 */
    private Integer time;
    /** 限制内容 */
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

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Long languageId) {
        this.languageId = languageId;
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
