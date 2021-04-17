package indi.snowmeow.zkoj.base.model.dto;

/**
 * @author snowmeow
 * @date 2021/3/23
 */
public class UserInsertDTO {

    private String username;

    private String password;

    private String name;

    private String ipAddress;

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

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
