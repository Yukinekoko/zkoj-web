package indi.snowmeow.zkoj.auth.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author snowmeow
 * @date 2021/4/13
 */
@Data
public class UmsUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 用户ID */
    private Long id;
    /* 用户名 */
    private String username;
    /* 密码 */
    private String password;
    /* 名称 */
    private String name;
    /* 头像url */
    private String faceUrl;
    /* 邮箱 */
    private String email;
    /* 简介 */
    private String introduce;
    /* 账号状态 */
    private Byte status;
    /* 创建IP */
    private String createIp;
    /* 创建日期 */
    private Date gmtCreate;
    /* 最后登录IP */
    private String lastIp;
    /* 最后登录时间 */
    private Date gmtModified;
}
