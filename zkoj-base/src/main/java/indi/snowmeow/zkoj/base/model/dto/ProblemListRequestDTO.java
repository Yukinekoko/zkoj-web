package indi.snowmeow.zkoj.base.model.dto;

import lombok.Data;

/**
 * @author snowmeow
 * @date 2021/2/14
 */
@Data
public class ProblemListRequestDTO {

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

}
