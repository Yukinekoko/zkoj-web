package indi.snowmeow.zkoj.base.model.dto;

import lombok.Data;

/**
 * @author snowmeow
 * @date 2021/4/19
 */
@Data
public class UserInfoUpdateDTO {

    private Long userId;

    private String name;

    private String email;

    private String description;

}
