package indi.snowmeow.zkoj.base.model.req;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author snowmeow
 * @date 2021/4/24
 */
@Data
public class SolutionListRequest {
    @Length(min = 6, max = 18)
    private String username;
    @Min(1)
    private Long problemId;
    @Min(1)
    @Max(10)
    private Long statusId;
    @Min(1)
    private Long solutionId;
    @Min(1)
    private Integer page;

}
