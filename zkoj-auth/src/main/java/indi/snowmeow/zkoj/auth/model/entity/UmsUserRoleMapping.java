package indi.snowmeow.zkoj.auth.model.entity;

import lombok.Data;

/**
 * @author snowmeow
 * @date 2021/4/13
 */
@Data
public class UmsUserRoleMapping {

    private Long id;

    private Long userId;

    private Long roleId;
}
