package indi.snowmeow.zkojweb.service;

import indi.snowmeow.zkojweb.model.dto.UserInsertDTO;
import indi.snowmeow.zkojweb.model.po.RolePO;
import indi.snowmeow.zkojweb.model.po.UserPO;
import indi.snowmeow.zkojweb.model.UserInfo;
import indi.snowmeow.zkojweb.model.req.UserRegisterRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * UserService接口
 *
 * @author snowmeow
 * @date 2020/10/13
 */
public interface UserService {

    Long getIdFromUsername(String username);

    /**
     * 获取用户数据插入数据库
     */
    int insert(UserRegisterRequest request, HttpServletRequest servletRequest);

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    public UserPO findByUserName(String username);

    /**
     * 根据昵称获取用户信息
     *
     * @param name 昵称
     * @return 用户信息
     */
    public UserPO findByName(String name);

    /**
     * 获取用户信息
     *
     * @param username 用户名
     * @return userInfo 用户信息对象
     */
    public UserInfo getUserInfo(String username);

    /**
     * 修改用户个人信息
     *
     * @param username
     * @param name
     * @param email
     * @param description
     * @return 数据更新行数
     */
    public int updateUserInfo(String username, String name, String email, String description);

    public Integer getUserSubmitCount(Long userId);

    public Integer getUserAcceptedCount(Long userId);

    public Integer getUserRank(Long userId);

    public List<Map<String, Object>> getRank(int page);

    // TODO: 耦合
    public int updateUserLastDate(String username);

    /**
     * 登录后更新用户的最后登录时间以及登录IP地址
     *
     * @param username 用户名
     * @param ip       IP地址
     * @return 是否成功
     */
    boolean updateLastDateAndLastIp(String username, String ip);

    public List<Map<String, Object>> getUserProblemTried(Long userId);

    public List<Map<String, Object>> getUserProblemAccepted(Long userId);

    public List<RolePO> getRoleList();

    public int updateUserPassword(String username, String password);
}
