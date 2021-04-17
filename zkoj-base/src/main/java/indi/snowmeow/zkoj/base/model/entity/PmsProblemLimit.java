package indi.snowmeow.zkoj.base.model.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @author snowmeow
 * @date 2021/4/15
 */
@Data
public class PmsProblemLimit implements Serializable {

    private Long id;

    private Long problemId;

    private Long languageId;

    private Integer memory;

    private Integer time;

    private Date gmtCreate;

    private Date gmtModified;

    private static final long serialVersionUID = 1L;
}