package indi.snowmeow.zkoj.base.model.req;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 换取问题列表接口参数DTO
 * @author snowmeow
 * @date 2021/2/14
 */
@Data
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
    private String searchText;

}
