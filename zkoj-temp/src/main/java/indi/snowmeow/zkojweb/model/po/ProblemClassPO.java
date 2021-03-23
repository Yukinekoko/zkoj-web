package indi.snowmeow.zkojweb.model.po;

/**
 * @author snowmeow
 * @date 2021/2/12
 */
public class ProblemClassPO {

    /* 唯一标识符 */
    private Long id;
    /* 分类名 */
    private String name;
    /* 分类详细描述 */
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
