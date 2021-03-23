package indi.snowmeow.zkojweb.service.impl;

import indi.snowmeow.zkojweb.mapper.RoleMapper;
import indi.snowmeow.zkojweb.model.dto.UserInsertDTO;
import indi.snowmeow.zkojweb.model.po.RolePO;
import indi.snowmeow.zkojweb.model.po.UserPO;
import indi.snowmeow.zkojweb.mapper.UserMapper;
import indi.snowmeow.zkojweb.model.UserInfo;
import indi.snowmeow.zkojweb.model.req.UserRegisterRequest;
import indi.snowmeow.zkojweb.service.UserService;
import indi.snowmeow.zkojweb.util.IpAddressUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/** UserService实现类
 * @author  snowmeow
 * @date    2020/10/13
 * */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    RoleMapper roleMapper;

    @Override
    public Long getIdFromUsername(String username) {
        return userMapper.getIdFromUserName(username);
    }

    @Override
    public int insert(UserRegisterRequest request, HttpServletRequest servletRequest) {
        UserPO userPO = new UserPO();
        BeanUtils.copyProperties(request, userPO);
        userPO.setCreateIp(IpAddressUtil.getIpAddress(servletRequest));
        userPO.setStatus((byte) 1);
        userPO.setRoleId(1L);
        return userMapper.insert(userPO);

    }

    @Override
    public UserPO findByUserName(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public UserPO findByName(String name) {
        return userMapper.findByName(name);
    }

    @Override
    public UserInfo getUserInfo(String username) {
        return userMapper.getUserInfo(username);
    }
    @Override
    public int updateUserInfo(String username,String name,String email,String description) {
        return userMapper.updateUserInfo(username,name,email,description);
    }

    @Override
    public Integer getUserSubmitCount(Long userId) {
        return userMapper.getUserSubmitCount(userId);
    }

    @Override
    public Integer getUserAcceptedCount(Long userId) {
        return userMapper.getUserAcceptedCount(userId);
    }

    @Override
    public Integer getUserRank(Long userId) {
        return userMapper.getUserRank(userId);
    }

    @Override
    public List<Map<String, Object>> getRank(int page) {
         page = page-1;
        return userMapper.getRank(page);
    }

    @Override
    public int updateUserLastDate(String username) {
        return userMapper.updateUserLastDate(username);
    }

    @Override
    public boolean updateLastDateAndLastIp(String username, String ip) {
        int result = userMapper.updateLastDateAndLastIp(username, ip);
        if (result >= 1) {
            return true;
        }
        return false;
    }

    @Override
    public List<Map<String, Object>> getUserProblemTried(Long userId) {
        return userMapper.getUserProblemTried(userId);
    }

    @Override
    public List<Map<String, Object>> getUserProblemAccepted(Long userId) {
        return userMapper.getUserProblemAccepted(userId);
    }

    @Override
    public List<RolePO> getRoleList() {
        return roleMapper.getRoleList();
    }

    @Override
    public int updateUserPassword(String username, String password) {
        return userMapper.updateUserPassword(username, password);
    }
}
