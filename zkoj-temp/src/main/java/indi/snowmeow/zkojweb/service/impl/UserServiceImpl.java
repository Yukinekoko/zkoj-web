package indi.snowmeow.zkojweb.service.impl;

import indi.snowmeow.zkojweb.mapper.RoleMapper;
import indi.snowmeow.zkojweb.model.Role;
import indi.snowmeow.zkojweb.model.po.UserPO;
import indi.snowmeow.zkojweb.mapper.UserMapper;
import indi.snowmeow.zkojweb.model.UserInfo;
import indi.snowmeow.zkojweb.service.UserService;
import indi.snowmeow.zkojweb.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public UserPO findByUserName(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public UserPO findByName(String name) {
        return userMapper.findByName(name);
    }

    @Override
    public int insert(UserPO userPO) {
        return userMapper.insert(userPO);
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
    public String generateJwtToken(String username) {
        return JwtUtil.encode(username, roleMapper.findByUsername(username).getName());
    }

    @Override
    public Role getRole(String username) {
        return roleMapper.findByUsername(username);
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
    public int updateUserStatus(String username ,Integer type) {
        return userMapper.updateUserStatus(username ,type);
    }

    @Override
    public List<Map<String, Object>> getUserList(int page,int limit,String search , Long roleId,Byte status) {
        int offset = (page - 1) * limit;
        return userMapper.getUserList(offset, limit, search,roleId, status);
    }

    @Override
    public int insertUsers(List<Map<String, Object>> userList) {
        return userMapper.insertUsers(userList);
    }

    @Override
    public List<Role> getRoleList() {
        return roleMapper.getRoleList();
    }

    @Override
    public int updateUserRole(String username, Long roleId) {
        return userMapper.updateUserRole(username, roleId);
    }

    @Override
    public int updateUserPassword(String username, String password) {
        return userMapper.updateUserPassword(username, password);
    }
}
