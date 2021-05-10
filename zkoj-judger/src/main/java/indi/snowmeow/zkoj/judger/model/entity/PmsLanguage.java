package indi.snowmeow.zkoj.judger.model.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author snowmeow
 * @date 2021/5/8
 */
@Data
public class PmsLanguage implements Serializable {

    private Long id;

    private String name;

    private static final long serialVersionUID = 1L;
}
