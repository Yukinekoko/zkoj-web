package indi.snowmeow.zkoj.base.model.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @author snowmeow
 * @date 2021/4/15
 */
@Data
public class PmsSolution implements Serializable {

    private Long id;

    private Long problemId;

    private Long userId;

    private Integer time;

    private Integer memory;

    private Date submitDate;

    private Long statusId;

    private Long languageId;

    private String errorMessage;

    private static final long serialVersionUID = 1L;
}