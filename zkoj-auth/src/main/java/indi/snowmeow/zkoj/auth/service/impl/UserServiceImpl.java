package indi.snowmeow.zkoj.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import indi.snowmeow.zkoj.auth.dao.UmsRoleMapper;
import indi.snowmeow.zkoj.auth.dao.UmsUserMapper;
import indi.snowmeow.zkoj.auth.dao.UmsUserRoleMappingMapper;
import indi.snowmeow.zkoj.auth.model.dto.UserLoginDTO;
import indi.snowmeow.zkoj.auth.model.entity.UmsRole;
import indi.snowmeow.zkoj.auth.model.entity.UmsUser;
import indi.snowmeow.zkoj.auth.model.entity.UmsUserRoleMapping;
import indi.snowmeow.zkoj.auth.service.UserService;
import indi.snowmeow.zkoj.base.common.base.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author snowmeow
 * @date 2021/4/13
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UmsRoleMapper umsRoleMapper;
    @Autowired
    UmsUserMapper umsUserMapper;
    @Autowired
    UmsUserRoleMappingMapper umsUserRoleMappingMapper;

    public UserLoginDTO findByUsername(String username) {
        QueryWrapper<UmsUser> userWrapper = new QueryWrapper<>();
        userWrapper.eq("username", username);
        UmsUser user = umsUserMapper.selectOne(userWrapper);
        if (user == null) {
            return null;
        }

        QueryWrapper<UmsUserRoleMapping> userRoleMappingWrapper = new QueryWrapper<>();
        userRoleMappingWrapper.eq("user_id", user.getId());
        List<UmsUserRoleMapping> userRoleMappingMapperList
            = umsUserRoleMappingMapper.selectList(userRoleMappingWrapper);

        QueryWrapper<UmsRole> roleWrapper = new QueryWrapper<>();
        List<Long> roleIds = new ArrayList<>();
        userRoleMappingMapperList.forEach( r -> roleIds.add(r.getRoleId()));
        roleWrapper.in("id", roleIds);
        List<UmsRole> roles = umsRoleMapper.selectList(roleWrapper);

        List<String> roleStrings = new ArrayList<>();
        roles.forEach(r -> roleStrings.add(r.getName()));
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setUsername(user.getUsername());
        userLoginDTO.setPassword(user.getPassword());
        userLoginDTO.setRoles(roleStrings);

        return userLoginDTO;
    }

    @Override
    public long getIdFromUsername(String username) {
        QueryWrapper<UmsUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        UmsUser user = umsUserMapper.selectOne(queryWrapper);
        if (null == user) {
            throw new RuntimeException("User Not Exits!");
        }
        return user.getId();
    }
}
