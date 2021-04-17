package indi.snowmeow.zkoj.base.model.req;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author snowmeow
 * @date 2021/2/21
 */
public class ProblemAdminListRequest {

    @Max(3)
    @Min(1)
    private Byte difficulty;

    private Long classId;

    private Long tagId;

    private String uploadUsername;

    private Integer uploadUserId;

    private String sortType;

    private String search;
    @Min(1)
    private Integer page;

    private Integer limit;

    @Override
    public String toString() {
        return "ProblemAdminListRequest{" +
                "difficulty=" + difficulty +
                ", classId=" + classId +
                ", tagId=" + tagId +
                ", uploadUsername='" + uploadUsername + '\'' +
                ", uploadUserId='" + uploadUserId + '\'' +
                ", sortType='" + sortType + '\'' +
                ", search='" + search + '\'' +
                ", page=" + page +
                ", limit=" + limit +
                '}';
    }

    public Byte getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Byte difficulty) {
        this.difficulty = difficulty;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getUploadUsername() {
        return uploadUsername;
    }

    public void setUploadUsername(String uploadUsername) {
        this.uploadUsername = uploadUsername;
    }

    public Integer getUploadUserId() {
        return uploadUserId;
    }

    public void setUploadUserId(Integer uploadUserId) {
        this.uploadUserId = uploadUserId;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
