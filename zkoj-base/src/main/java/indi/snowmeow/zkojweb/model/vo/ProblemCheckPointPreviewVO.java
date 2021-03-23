package indi.snowmeow.zkojweb.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * @author snowmeow
 * @date 2021/2/21
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProblemCheckPointPreviewVO {

    private String input;

    private String output;

    @Override
    public String toString() {
        return "ProblemCheckPointPreviewVO{" +
                "input='" + input + '\'' +
                ", output='" + output + '\'' +
                '}';
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
