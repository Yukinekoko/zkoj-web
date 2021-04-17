package indi.snowmeow.zkoj.base.model.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @author snowmeow
 * @date 2021/4/15
 */
@Data
public class UmsUser implements Serializable {

    private Long id;

    private String username;

    private String password;

    private String name;

    private String faceUrl;

    private String email;

    private String description;

    private Byte status;

    private String createIp;

    private String lastIp;

    private Date gmtCreate;

    private Date gmtModified;

    private static final long serialVersionUID = 1L;
}