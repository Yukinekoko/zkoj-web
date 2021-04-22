package indi.snowmeow.zkoj.base.model.req;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

/**
 * @author snowmeow
 * @date 2021/4/19
 */
@Data
public class UserInfoUpdateRequest {

    @Size(min = 1, max = 10)
    private String name;
    @Email
    private String email;
    @Size(max = 255)
    private String introduce;
}
