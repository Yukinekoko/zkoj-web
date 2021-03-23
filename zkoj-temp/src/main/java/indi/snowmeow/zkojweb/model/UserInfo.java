package indi.snowmeow.zkojweb.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Date;

/**
 * 可以返回给前端的UserInfo
 * @author snowmeow
 * @date 2020/11/1
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserInfo {

    /* 用户名 */
    private String username;
    /* 名称 */
    private String name;
    /* 头像url */
    private String faceUrl;
    /* 邮箱 */
    private String email;
    /* 简介 */
    private String description;
    /* 注册日期 */
    private Date createDate;
    /* 最后登录日期 */
    private Date lastDate;
    /* 通过数量 */
    private Integer acceptedCount;
    /* 提交数量 */
    private Integer submitCount;
    /* 排名 */
    private Integer rank;

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }
    public Integer getAcceptedCount() {
        return acceptedCount;
    }

    public void setAcceptedCount(Integer acceptedCount) {
        this.acceptedCount = acceptedCount;
    }

    public Integer getSubmitCount() {
        return submitCount;
    }

    public void setSubmitCount(Integer submitCount) {
        this.submitCount = submitCount;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
