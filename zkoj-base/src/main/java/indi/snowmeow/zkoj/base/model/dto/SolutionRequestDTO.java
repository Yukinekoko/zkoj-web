package indi.snowmeow.zkoj.base.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author snowmeow
 * @date 2021/5/21
 */
@Data
public class SolutionRequestDTO implements Serializable {

    private Long solutionId;

    private Long problemId;

    private Long languageId;

    private Short problemVersion;

    private Integer timeLimit;

    private Integer memoryLimit;

    private static final long serialVersionUID = 1L;
}
