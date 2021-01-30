package indi.snowmeow.zkojweb.mapper;

import indi.snowmeow.zkojweb.model.User;
import indi.snowmeow.zkojweb.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/** UserMapper
 * @author  snowmeow
 * @date    2020/10/13
 * */
@Mapper
public interface UserMapper {

    public User findByUsername(String username);

    public User findByName(String name);

    public int  insert(User user);

    public UserInfo getUserInfo(String username);

    public int updateUserInfo(String username,String name,String email,String description);
    /**
     * 更新用户的最后登录时间以及IP地址
     * @param username 用户名
     * @param lastIp 最后登录时间
     * @return 数据表影响行数
     * */
    public int updateLastDateAndLastIp(@Param("username") String username, @Param("last_ip") String lastIp);

    public Integer getUserSubmitCount(Long userId);

    public Integer getUserAcceptedCount(Long userId);

    public Integer getUserRank(Long userId);

    public List<Map<String, Object>> getRank(int page);

    public int updateUserLastDate(String username);

    public List<Map<String, Object>> getUserProblemTried(Long userId);

    public List<Map<String, Object>> getUserProblemAccepted(Long userId);
    //**
    public int insertUsers(List<Map<String, Object>> userList);

    public int updateUserStatus(String username , Integer type);

    public List<Map<String, Object>> getUserList(int offset,int limit,String search , Long roleId,Byte status);

    public int updateUserRole(String username ,Long roleId);

    public int updateUserPassword(String username,String password);
}
