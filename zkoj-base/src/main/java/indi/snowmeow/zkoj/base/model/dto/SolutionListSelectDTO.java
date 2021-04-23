package indi.snowmeow.zkoj.base.model.dto;

import lombok.Data;

/**
 * @author snowmeow
 * @date 2021/4/24
 */
@Data
public class SolutionListSelectDTO {

    private String username;

    private Long problemId;

    private Long statusId;

    private Long solutionId;

    private Integer page;

    private Integer limit;

    private Integer offset;
}
