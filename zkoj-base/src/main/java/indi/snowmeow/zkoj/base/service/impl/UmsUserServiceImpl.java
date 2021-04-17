package indi.snowmeow.zkoj.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import indi.snowmeow.zkoj.base.dao.UmsUserMapper;
import indi.snowmeow.zkoj.base.model.entity.UmsUser;
import indi.snowmeow.zkoj.base.service.UmsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author snowmeow
 * @date 2021/4/17
 */
@Service
public class UmsUserServiceImpl implements UmsUserService {

    @Autowired
    UmsUserMapper umsUserMapper;

    @Override
    public UmsUser getFromUsername(String username) {
        QueryWrapper<UmsUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return umsUserMapper.selectOne(queryWrapper);
    }
}
