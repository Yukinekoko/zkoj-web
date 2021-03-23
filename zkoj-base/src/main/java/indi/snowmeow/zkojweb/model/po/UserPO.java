package indi.snowmeow.zkojweb.model.po;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Date;

/** User表
 * @author  snowmeow
 * @date    2020/10/13
 * */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserPO {

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
    private String description;
    /* 账号状态 */
    private Byte status;
    /* 创建IP */
    private String createIp;
    /* 创建日期 */
    private Date createDate;
    /* 最后登录IP */
    private String lastIp;
    /* 最后登录时间 */
    private Date lastDate;
    /* 权限ID */
    private Long roleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getCreateIp() {
        return createIp;
    }

    public void setCreateIp(String createIp) {
        this.createIp = createIp;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getLastIp() {
        return lastIp;
    }

    public void setLastIp(String lastIp) {
        this.lastIp = lastIp;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
