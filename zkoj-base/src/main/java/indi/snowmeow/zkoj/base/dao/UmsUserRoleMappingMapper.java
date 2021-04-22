package indi.snowmeow.zkoj.base.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import indi.snowmeow.zkoj.base.model.entity.UmsUserRoleMapping;

/**
 * @author snowmeow
 * @date 2021/4/20
 */
public interface UmsUserRoleMappingMapper extends BaseMapper<UmsUserRoleMapping> {

    int insert(UmsUserRoleMapping userRoleMapping);
}
