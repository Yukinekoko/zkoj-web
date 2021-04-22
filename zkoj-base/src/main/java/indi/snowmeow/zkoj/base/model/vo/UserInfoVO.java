package indi.snowmeow.zkoj.base.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.Date;

/**
 * @author snowmeow
 * @date 2021/4/17
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserInfoVO {

    private String userName;

    private String name;

    private String faceUrl;

    private String email;

    private String introduce;

    private Date createDate;

    private Date lastDate;

    private Integer acceptedCount;

    private Integer submitCount;

    private Integer rank;
}
