package indi.snowmeow.zkoj.base.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.io.Serializable;

/**
 * @author snowmeow
 * @date 2021/4/19
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SolutionRankVO implements Serializable {

    private Integer rank;

    private String username;

    private String name;

    private Integer count;

    private Integer accepted;

    private static final long serialVersionUID = 1L;
}
