package indi.snowmeow.zkojweb.model;

/**
 * Role对象
 * @author snowmeow
 * @date 2021/1/3
 */
public class Role {

    /** ID */
    private Long id;
    /** 权限名 */
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
