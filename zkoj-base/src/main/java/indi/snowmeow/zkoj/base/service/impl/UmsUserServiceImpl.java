package indi.snowmeow.zkoj.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import indi.snowmeow.zkoj.api.auth.service.EncodePasswordService;
import indi.snowmeow.zkoj.base.common.base.BaseException;
import indi.snowmeow.zkoj.base.common.enums.ResultCodeEnum;
import indi.snowmeow.zkoj.base.common.util.AuthenticationUtil;
import indi.snowmeow.zkoj.base.common.util.IpAddressUtil;
import indi.snowmeow.zkoj.base.common.util.JwtUtil;
import indi.snowmeow.zkoj.base.dao.UmsUserMapper;
import indi.snowmeow.zkoj.base.model.dto.UserInfoUpdateDTO;
import indi.snowmeow.zkoj.base.model.dto.UserRegisterDTO;
import indi.snowmeow.zkoj.base.model.entity.UmsUser;
import indi.snowmeow.zkoj.base.service.UmsUserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author snowmeow
 * @date 2021/4/17
 */
@Service
public class UmsUserServiceImpl implements UmsUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UmsUserMapper.class);

    @DubboReference(version = "1.0.0")
    EncodePasswordService encodePasswordService;
    @Autowired
    UmsUserMapper umsUserMapper;

    @Override
    public UmsUser find(long id) {
        return umsUserMapper.selectById(id);
    }

    @Override
    public UmsUser findFromUsername(String username) {
        QueryWrapper<UmsUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return umsUserMapper.selectOne(queryWrapper);
    }

    @Override
    public void updateBaseInfo(UserInfoUpdateDTO requestDTO) {
        String token = AuthenticationUtil.getToken();
        Assert.notNull(token, "token is null");
        requestDTO.setUserId(JwtUtil.getUserId(token));
        if (umsUserMapper.updateBaseInfo(requestDTO) != 1) {
            LOGGER.error("User Update Error - UserId: {}", requestDTO.getUserId());
            throw new BaseException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }

    // TODO 刷新TOKEN
    @Override
    public void updatePassword(String oldPassword, String newPassword) {
        String token = AuthenticationUtil.getToken();
        Assert.notNull(token, "token is null");
        UmsUser user = umsUserMapper.selectById(JwtUtil.getUserId(token));
        if (null == user) {
            LOGGER.error("User Password Update Error - UmsUser is null");
            throw new BaseException(ResultCodeEnum.SYSTEM_ERROR);
        }
        String encodeOldPassword = encodePasswordService.encodePassword(oldPassword);
        if (!user.getPassword().equals(encodeOldPassword)) {
            throw new BaseException(ResultCodeEnum.USER_PASSWORD_ERROR);
        }
        String encodeNewPassword = encodePasswordService.encodePassword(newPassword);
        int result = umsUserMapper.updatePassword(user.getId(), encodeNewPassword);
        if (result != 1) {
            LOGGER.error("User Password Update Error - UserId: {}", user.getId());
            throw new BaseException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public long insert(UserRegisterDTO requestDTO) {
        UmsUser verifyUsername = findFromUsername(requestDTO.getUsername());
        if (null != verifyUsername) {
            throw new BaseException(ResultCodeEnum.USER_USERNAME_EXIST);
        }
        UmsUser user = new UmsUser();
        user.setUsername(requestDTO.getUsername());
        user.setName(requestDTO.getName());
        user.setPassword(encodePasswordService.encodePassword(requestDTO.getPassword()));
        user.setCreateIp(IpAddressUtil.getIpAddress());
        user.setLastIp(user.getCreateIp());
        user.setStatus((byte) 1);
        if (umsUserMapper.insert(user) != 1) {
            LOGGER.error("User Insert Error - Username: {}", requestDTO.getUsername());
            throw new BaseException(ResultCodeEnum.SYSTEM_ERROR);
        }
        return user.getId();
    }
}
