package indi.snowmeow.zkoj.base.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

/**
 * @author snowmeow
 * @date 2021/2/12
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProblemTagVO {

    /* 唯一标识符 */
    private Long id;
    /* 算法标签名 */
    private String name;
    /* 用于该标签的问题总数 */
    private int count;

}
