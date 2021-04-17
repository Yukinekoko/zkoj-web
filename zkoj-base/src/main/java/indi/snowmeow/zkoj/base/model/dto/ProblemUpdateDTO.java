package indi.snowmeow.zkoj.base.model.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author snowmeow
 * @date 2021/2/20
 */
public class ProblemUpdateDTO {

    @NotNull
    private Long id;

    private String description;
    @Max(40)
    private String title;

    private String sampleInput;

    private String sampleOutput;

    private String hint;

    private Long problemClass;

    private List<Long> tag;

    private Byte difficulty;

    private Boolean problemPrivate;

    @Override
    public String toString() {
        return "ProblemUpdateRequest{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", sampleInput='" + sampleInput + '\'' +
                ", sampleOutput='" + sampleOutput + '\'' +
                ", hint='" + hint + '\'' +
                ", problemClass=" + problemClass +
                ", tag=" + tag +
                ", difficulty=" + difficulty +
                ", problemPrivate=" + problemPrivate +
                '}';
    }

    public Boolean getProblemPrivate() {
        return problemPrivate;
    }

    public void setProblemPrivate(Boolean problemPrivate) {
        this.problemPrivate = problemPrivate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Long getProblemClass() {
        return problemClass;
    }

    public void setProblemClass(Long problemClass) {
        this.problemClass = problemClass;
    }

    public List<Long> getTag() {
        return tag;
    }

    public void setTag(List<Long> tag) {
        this.tag = tag;
    }

    public Byte getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Byte difficulty) {
        this.difficulty = difficulty;
    }
}
