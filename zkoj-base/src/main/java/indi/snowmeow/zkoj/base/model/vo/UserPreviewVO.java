package indi.snowmeow.zkoj.base.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * @author snowmeow
 * @date 2021/2/21
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserPreviewVO {

    private Long id;

    private String username;

    private String name;

}
