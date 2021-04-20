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
public class UserSolutionRankStatisticsVO implements Serializable {

    private Integer submitCount;

    private Integer acceptedCount;

    private Integer rank;

    private static final long serialVersionUID = 1L;

}
