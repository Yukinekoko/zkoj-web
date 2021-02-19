package indi.snowmeow.zkojweb.req;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 换取问题列表接口参数DTO
 * @author snowmeow
 * @date 2021/2/14
 */
public class ProblemListRequest {

    // TODO 是否可以设置默认值？
    /** 页码 */
    @NotNull
    @Min(1)
    private Integer page;
    /** 每页数量 */
    @NotNull
    @Min(1)
    @Max(100)
    private Integer limit;
    /** 难度筛选 */
    @Min(1)
    @Max(3)
    private Byte difficulty;
    /** 分组筛选 */
    @Min(0)
    private Long classId;
    /** 标签筛选 */
    @Min(0)
    private Long tagId;
    /** 搜索关键字 */
    @Size(min = 1, max = 20)
    private String search;

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

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
