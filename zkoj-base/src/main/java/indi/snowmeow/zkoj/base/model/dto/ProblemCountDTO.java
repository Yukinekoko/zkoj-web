package indi.snowmeow.zkoj.base.model.dto;

import lombok.Data;
import org.apache.ibatis.annotations.Param;

/**
 * @author snowmeow
 * @date 2021/2/20
 */
@Data
public class ProblemCountDTO {

    /** 难度筛选 */
    private Byte difficulty;
    /** 分组筛选 */
    private Long classId;
    /** 标签筛选 */
    private Long tagId;

    private Byte status;

    private Long userId;

}
