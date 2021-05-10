package indi.snowmeow.zkoj.judger.model.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author snowmeow
 * @date 2021/5/10
 */
@Data
public class PmsProblemCheckPoint implements Serializable {

    private Long id;

    private Long problemId;

    private String input;

    private String output;

    private static final long serialVersionUID = 1L;
}
