package indi.snowmeow.zkoj.base.model.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * @author snowmeow
 * @date 2021/4/15
 */
@Data
public class PmsProblemCheckPoint implements Serializable {

    private Long id;

    private Long problemId;

    private String input;

    private String output;

    private static final long serialVersionUID = 1L;
}