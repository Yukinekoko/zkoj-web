package indi.snowmeow.zkoj.base.model.req;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author snowmeow
 * @date 2021/3/23
 */
@Data
public class UserRegisterRequest {


    @Length(min = 6, max = 18)
    private String username;
    @Length(min = 32, max = 32)
    private String password;
    @Length(min = 1, max = 10)
    private String name;

}
