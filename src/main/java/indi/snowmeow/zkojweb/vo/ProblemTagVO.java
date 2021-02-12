package indi.snowmeow.zkojweb.vo;

/**
 * @author snowmeow
 * @date 2021/2/12
 */
public class ProblemTagVO {

    /* 唯一标识符 */
    private Long id;
    /* 算法标签名 */
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
