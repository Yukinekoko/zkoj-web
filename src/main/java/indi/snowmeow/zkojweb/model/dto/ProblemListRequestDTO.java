package indi.snowmeow.zkojweb.model.dto;

/**
 * @author snowmeow
 * @date 2021/2/14
 */
public class ProblemListRequestDTO {

    // TODO 是否可以设置默认值？
    /** 页码 */
    private Integer page;
    /** 每页数量 */
    private Integer limit;
    /** 难度筛选 */
    private Byte difficulty;
    /** 分组筛选 */
    private Long classId;
    /** 标签筛选 */
    private Long tagId;
    /** 搜索关键字 */
    private String searchText;
    /** 偏移值 */
    private Integer offset;
    /** 用户ID */
    private Long userId;

    @Override
    public String toString() {
        return "ProblemListRequestDTO{" +
                "page=" + page +
                ", limit=" + limit +
                ", difficulty=" + difficulty +
                ", classId=" + classId +
                ", tagId=" + tagId +
                ", searchText='" + searchText + '\'' +
                ", offset=" + offset +
                ", userId=" + userId +
                '}';
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}
