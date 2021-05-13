package indi.snowmeow.zkoj.judger.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author snowmeow
 * @date 2021/5/8
 */
@Data
public class SolutionRequest implements Serializable {

    private Long solutionId;

    private Long problemId;

    private Long languageId;

    private Short problemVersion;

    private Integer timeLimit;

    private Integer memoryLimit;

    private static final long serialVersionUID = 1L;
}
