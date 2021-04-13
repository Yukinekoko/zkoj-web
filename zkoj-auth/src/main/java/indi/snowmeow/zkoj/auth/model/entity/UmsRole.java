package indi.snowmeow.zkoj.auth.model.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author snowmeow
 * @date 2021/4/13
 */
@Data
public class UmsRole {

    private Long id;

    private String name;

    private Date gmtCreate;

    private Date gmtModified;
}
