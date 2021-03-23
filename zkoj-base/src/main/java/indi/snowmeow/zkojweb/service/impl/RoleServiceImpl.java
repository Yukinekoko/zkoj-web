package indi.snowmeow.zkojweb.service.impl;

import indi.snowmeow.zkojweb.mapper.RoleMapper;
import indi.snowmeow.zkojweb.model.po.RolePO;
import indi.snowmeow.zkojweb.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author snowmeow
 * @date 2021/3/23
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleMapper roleMapper;

    @Override
    public RolePO getFromUsername(String username) {
        return roleMapper.findByUsername(username);
    }
}
