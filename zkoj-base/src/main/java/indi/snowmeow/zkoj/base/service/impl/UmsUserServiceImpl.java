package indi.snowmeow.zkoj.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import indi.snowmeow.zkoj.base.common.base.BaseException;
import indi.snowmeow.zkoj.base.common.enums.ResultCodeEnum;
import indi.snowmeow.zkoj.base.common.util.AuthenticationUtil;
import indi.snowmeow.zkoj.base.common.util.JwtUtil;
import indi.snowmeow.zkoj.base.dao.UmsUserMapper;
import indi.snowmeow.zkoj.base.model.dto.UserInfoUpdateDTO;
import indi.snowmeow.zkoj.base.model.entity.UmsUser;
import indi.snowmeow.zkoj.base.service.UmsUserService;
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

    @Autowired
    UmsUserMapper umsUserMapper;

    @Override
    public UmsUser getFromUsername(String username) {
        QueryWrapper<UmsUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return umsUserMapper.selectOne(queryWrapper);
    }

    @Override
    public void update(UserInfoUpdateDTO requestDTO) {
        String token = AuthenticationUtil.getToken();
        Assert.notNull(token, "token is null");
        requestDTO.setUserId(JwtUtil.getUserId(token));
        if (umsUserMapper.update(requestDTO) != 1) {
            LOGGER.error("User Update Error - UserId: {}", requestDTO.getUserId());
            throw new BaseException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }
}
