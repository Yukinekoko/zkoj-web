package indi.snowmeow.zkoj.base.model.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author snowmeow
 * @date 2021/4/20
 */
@Data
public class UserRegisterDTO {

    private String username;

    private String password;

    private String name;
}
