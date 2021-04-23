package indi.snowmeow.zkoj.base.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.Date;

/**
 * @author snowmeow
 * @date 2021/4/24
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SolutionPreviewVO {

    private Long id;

    private Integer time;

    private Integer memory;

    private Date submitDate;

    private SolutionUserVO user;

    private SolutionProblemVO problem;

    private SolutionStatusVO status;

    private LanguageVO language;


}
