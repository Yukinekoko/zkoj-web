package indi.snowmeow.zkoj.base.model.req;

import org.hibernate.validator.constraints.Length;

/**
 * @author snowmeow
 * @date 2021/3/23
 */
public class UserRegisterRequest {

    @Length(min = 6, max = 18)
    private String username;
    @Length(min = 32, max = 32)
    private String password;
    @Length(min = 1, max = 10)
    private String name;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
