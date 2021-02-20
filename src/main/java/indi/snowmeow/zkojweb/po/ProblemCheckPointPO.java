package indi.snowmeow.zkojweb.po;

/**
 * @author snowmeow
 * @date 2021/2/21
 */
public class ProblemCheckPointPO {

    private Long id;

    private Long problemId;

    private String input;

    private String output;

    @Override
    public String toString() {
        return "ProblemCheckPointPO{" +
                "id=" + id +
                ", problemId=" + problemId +
                ", input='" + input + '\'' +
                ", output='" + output + '\'' +
                '}';
    }

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

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
