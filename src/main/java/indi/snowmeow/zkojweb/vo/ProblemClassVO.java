package indi.snowmeow.zkojweb.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * @author snowmeow
 * @date 2021/2/12
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProblemClassVO {

    /* 唯一标识符 */
    private Long id;
    /* 分类名 */
    private String name;
    /** 描述 */
    private String description;



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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
