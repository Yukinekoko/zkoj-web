package indi.snowmeow.zkoj.base.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import indi.snowmeow.zkoj.base.model.dto.UserInfoUpdateDTO;
import indi.snowmeow.zkoj.base.model.entity.UmsUser;

/**
 * @author snowmeow
 * @date 2021/4/17
 */
public interface UmsUserMapper extends BaseMapper<UmsUser> {

    /**
     * 更新用户信息
     * */
    int updateBaseInfo(UserInfoUpdateDTO requestDTO);

    /**
     * 更新密码
     * */
    int updatePassword(Long userId, String password);
}
