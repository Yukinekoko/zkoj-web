package indi.snowmeow.zkojweb.util;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.context.annotation.Bean;
import java.io.Serializable;
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserExcelModel   implements Serializable {

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

    @ExcelProperty(value = "用户名", index = 0)
    private String username;
    @ExcelProperty(value = "密码", index = 1)
    private String password;
    @ExcelProperty(value = "昵称", index = 2)
    private String name;

}
