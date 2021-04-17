package indi.snowmeow.zkoj.base.model.req;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author snowmeow
 * @date 2021/2/20
 */
@Data
public class ProblemCountRequest {

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
    /** 用户做题状态筛选 */
    @Min(0)
    @Max(2)
    private Byte status;

}
