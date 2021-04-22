package indi.snowmeow.zkoj.base.service.impl;

import indi.snowmeow.zkoj.base.common.base.BaseException;
import indi.snowmeow.zkoj.base.common.enums.ResultCodeEnum;
import indi.snowmeow.zkoj.base.dao.UmsUserRoleMappingMapper;
import indi.snowmeow.zkoj.base.model.entity.UmsUserRoleMapping;
import indi.snowmeow.zkoj.base.service.UmsUserRoleMappingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author snowmeow
 * @date 2021/4/20
 */
@Service
public class UmsUserRoleMappingServiceImpl implements UmsUserRoleMappingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UmsUserRoleMappingServiceImpl.class);
    @Autowired
    UmsUserRoleMappingMapper umsUserRoleMappingMapper;

    @Override
    public void insert(Long userId, Long roleId) {
        UmsUserRoleMapping umsUserRoleMapping = new UmsUserRoleMapping();
        umsUserRoleMapping.setUserId(userId);
        umsUserRoleMapping.setRoleId(roleId);
        if (umsUserRoleMappingMapper.insert(umsUserRoleMapping) != 1) {
            LOGGER.error("UserRoleMapping Insert Error - userId:{}; roleId:{}", userId, roleId);
            throw new BaseException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }
}
