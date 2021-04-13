package indi.snowmeow.zkoj.auth.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author snowmeow
 * @date 2021/4/13
 */
@Data
public class UserLoginDTO implements Serializable {

    private String username;

    private String password;

    private List<String> roles;
}
