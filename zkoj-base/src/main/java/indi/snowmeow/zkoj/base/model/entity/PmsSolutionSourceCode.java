package indi.snowmeow.zkoj.base.model.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * @author snowmeow
 * @date 2021/4/15
 */
@Data
public class PmsSolutionSourceCode implements Serializable {

    private Long id;

    private Long solutionId;

    private String sourceCode;

    private static final long serialVersionUID = 1L;
}