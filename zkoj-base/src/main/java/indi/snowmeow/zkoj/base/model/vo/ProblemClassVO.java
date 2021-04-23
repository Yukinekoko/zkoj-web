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
public class ProblemClassVO {

    /* 唯一标识符 */
    private Long id;
    /* 分类名 */
    private String name;
    /** 描述 */
    private String introduce;
    /** 属于该分类的问题总数 */
    private Integer count;

}
