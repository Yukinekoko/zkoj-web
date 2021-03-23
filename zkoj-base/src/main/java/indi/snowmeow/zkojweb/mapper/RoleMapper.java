package indi.snowmeow.zkojweb.mapper;

import indi.snowmeow.zkojweb.model.po.RolePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * RoleMapper
 * @author snowmeow
 * @date 2021/1/3
 */
@Mapper
public interface RoleMapper {

    /**
     * 根据username获取Role对象
     * @param username 用户名
     * @return 用户的Role对象
     * */
    RolePO findByUsername(@Param("username") String username);

    List<RolePO> getRoleList();
}
