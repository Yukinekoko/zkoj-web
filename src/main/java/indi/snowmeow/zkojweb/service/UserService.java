package indi.snowmeow.zkojweb.service;

import indi.snowmeow.zkojweb.model.Role;
import indi.snowmeow.zkojweb.model.User;
import indi.snowmeow.zkojweb.model.UserInfo;

import java.util.List;
import java.util.Map;

/** UserService接口
 * @author  snowmeow
 * @date    2020/10/13
 * */
public interface UserService {

    /** 根据用户名获取用户信息
     * @param username 用户名
     * @return 用户信息
     * */
    public User findByUserName(String username);
   /** 根据昵称获取用户信息
     * @param name 昵称
     * @return 用户信息
     * */
    public User findByName(String name);
    /** 获取用户数据插入数据库
     * @param  user 用户对象
     * @return 数据更新行数
     * */
    public int insert(User user);
    /**
     * 获取用户信息
     * @param username 用户名
     * @return userInfo 用户信息对象
     *
     */
    public UserInfo getUserInfo(String username);
    /**
     * 修改用户个人信息
     * @param username
     * @param name
     * @param email
     * @param description
     * @return 数据更新行数
     */
    public int updateUserInfo(String username,String name,String email,String description);
    /** 构建jwt token
     * @param username 用户名
     * @return jwt-token
     * */
    public String generateJwtToken(String username);
    /**
     * 获取指定用户的权限对象
     * @param username 用户名
     * @return 权限对象
     * */
    public Role getRole(String username);

    public Integer getUserSubmitCount(Long userId);

    public Integer getUserAcceptedCount(Long userId);

    public Integer getUserRank(Long userId);

    public List<Map<String, Object>> getRank(int page);
    // TODO: 耦合
    public int updateUserLastDate(String username);
    /**
     * 登录后更新用户的最后登录时间以及登录IP地址
     * @param username 用户名
     * @param ip IP地址
     * @return 是否成功
     * */
    boolean updateLastDateAndLastIp(String username, String ip);

    public List<Map<String, Object>> getUserProblemTried(Long userId);

    public List<Map<String, Object>> getUserProblemAccepted(Long userId);

    public int updateUserStatus(String username,Integer type);

    public List<Map<String, Object>> getUserList(int page,int limit,String search , Long roleId,Byte status);

    public int insertUsers(List<Map<String, Object>> userList);

  public List<Role> getRoleList();

 public int updateUserRole(String username ,Long roleId);

 public int updateUserPassword(String username, String password);
}
