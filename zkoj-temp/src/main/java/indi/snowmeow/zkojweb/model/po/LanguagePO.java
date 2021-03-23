package indi.snowmeow.zkojweb.model.po;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * 对应数据库中的language表
 * @author snowmeow
 * @date 2020/11/10
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class LanguagePO {

    /** 唯一标识符 */
    private Long id;
    /** 语言名 */
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
