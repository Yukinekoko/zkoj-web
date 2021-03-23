package indi.snowmeow.zkojweb.controller;

import com.alibaba.excel.EasyExcel;
import indi.snowmeow.zkojweb.model.Role;
import indi.snowmeow.zkojweb.model.po.UserPO;
import indi.snowmeow.zkojweb.model.UserInfo;
import indi.snowmeow.zkojweb.service.impl.UserServiceImpl;
import indi.snowmeow.zkojweb.util.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** UserController
 * @author  snowmeow
 * @date    2021/01/26
 * */
@RestController
public class UserController {

    @Autowired
    UserServiceImpl userService;


    /**
     * 登录接口
     * @param requestBody - {username, password}
     * */
    @PostMapping("/login")
    public Object login(HttpServletRequest request,
                        @RequestBody Map<String, Object> requestBody) {
        String username = (String) requestBody.get("username");
        String password = (String) requestBody.get("password");

        // 非法数据
        if (!isLegal(username, password)) {
            return BaseBody.fail("Param Error");
        }

        password = EncodeUtil.encodePassword(password);

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
        } catch (UnknownAccountException | IncorrectCredentialsException e) {
            // 未知用户名
            // 密码错误
            return BaseBody.fail("Wrong Username Or Password.");
        } catch (DisabledAccountException e) {
            // 账号不可用
            return BaseBody.fail("Account Not Available.");
        } catch (Exception e) {
            return BaseBody.fail("Login Failure.");
        }
        //修改最后登录IP地址以及最后登录时间
        String ip = getIPAddress(request);
        userService.updateLastDateAndLastIp(username, ip);

        UserPO userPO = userService.findByUserName(username);
        Role role = userService.getRole(username);
        String name = userPO.getName();
        String roleName = role.getName();
        String jwtToken = userService.generateJwtToken(username);
        BaseBody<Map<String, Object>> responseBody = new BaseBody<>();
        responseBody.setMessage("Success");
        responseBody.setStatus(1);
        responseBody.setData(new HashMap<>());
        responseBody.getData().put("name", name);
        responseBody.getData().put("role", roleName);
        return ResponseEntity.ok().header("Authorization", "Bearer " + jwtToken)
                .header("Access-Control-Expose-Headers", "Authorization")
                .body(responseBody);

    }

    /**
     *  权限禁用
     * @requestbody
     * @return 影响函数
     */
    //TODO
    @RequiresRoles(value = {"SUPER_ADMIN","ADMIN"}, logical = Logical.OR)
    @PutMapping("/user/status")
    public Object updateUserStatus(@RequestBody Map<String, Object> requestBody){
        List<String> usernameList =(List<String>) requestBody.get("username_list");
        if (usernameList == null) {
            return BaseBody.fail("UsernameList Is Null");
        }
       Integer type = (Integer) requestBody.get("type");
        if (type == null) {
            return BaseBody.fail("Type Is Null");
        }
        //status 不是1就是被禁用了
        for (String username: usernameList ){
            int Success =  userService.updateUserStatus(username,type);
           if (Success <1) {
               return BaseBody.fail("Update fail");
           }
        }

        BaseBody body = new BaseBody();
        body.setStatus(1);
        body.setMessage("Success");

        return body;

    }
    /**
     * 获取角色权限列表
     * @return List<Map<String,Object>>
     */
        @RequiresRoles(value = {"SUPER_ADMIN","ADMIN"}, logical = Logical.OR)
        @GetMapping("/role")
        public Object getRoleList(){
            List<Role> roleList = userService.getRoleList();
            Map<String, Object> data = new HashMap<> ();
            data.put("role_list",roleList);
            BaseBody body = new BaseBody();
            body.setStatus(1);
            body.setMessage("Success");
            body.setData(data);
            return body;
         }

    /**
     * 获取用户列表
     * @return Map<String ,Object>
     */
    @RequiresRoles(value = {"SUPER_ADMIN","ADMIN"}, logical = Logical.OR)
    @GetMapping("/manage/user")
    public Object getUserList(@RequestParam(defaultValue = "1") int page,
                              @RequestParam(defaultValue = "50") int limit,
                              @RequestParam(required = false) String search,
                              @RequestParam(required = false, value = "role_id")  Long roleId,
                              @RequestParam(required = false) Byte status){


        List<Map<String, Object>> userList = userService.getUserList(page, limit, search,roleId, status);
        Map<String, Object> data = new HashMap<> ();
        int count = userList.size();
        data.put("user_list",userList);
        data.put("count",count);
        BaseBody<Map<String, Object>> body = new BaseBody<> ();
        body.setStatus(1);
        body.setMessage("Success");
        body.setData(data);
        return body;
    }


    /**
     * 修改其他账号密码 超级管理员
     */
    @RequiresRoles(value = {"SUPER_ADMIN"}, logical = Logical.OR)
    @PutMapping("/manage/user/password")
    public Object updateUserPasswordByAdmin(@RequestBody Map<String, Object> requestBody){
        String username = (String) requestBody.get("username");
        String password = (String) requestBody.get("password");
        if (!isLegal(username, password))
        {
            return BaseBody.fail("Param is illegal");
        }
        String encodedPassword = EncodeUtil.encodePassword(password);
        int Success = userService.updateUserPassword(username, encodedPassword);
        if (Success<1){
            return BaseBody.fail("Update fail");
        }
        BaseBody body = new BaseBody();
        body.setMessage("Success");
        body.setStatus(1);
        return body;
    }
    /**
     *  用户修改密码 普通用户
     */
    @PutMapping("/user/password")
    public Object updateUserPassword(@RequestBody Map<String, Object> requestBody){

        String username = (String) requestBody.get("username");
        String password = (String) requestBody.get("password");
        if (!isLegal(username, password)){
            return BaseBody.fail("params is illegal");
        }
        String encodedPassword = EncodeUtil.encodePassword(password);
        UserPO userPO = userService.findByUserName(username);
        String oldPassword = userPO.getPassword();
        if (!oldPassword.equals(encodedPassword)){
            return BaseBody.fail("origin password wrong");
        }
        String newPassword = (String) requestBody.get("new_password");
        String encodedNewPassword = EncodeUtil.encodePassword(password);
        if (oldPassword.equals(encodedNewPassword)){
            return BaseBody.fail("Password too similar");
        }
        int Success = userService.updateUserPassword(username,encodedNewPassword);
        if (Success<1){
            return BaseBody.fail("update password");
        }
        BaseBody body = new BaseBody();
        body.setStatus(1);
        body.setMessage("Success");
        return body;

    }
    /**
     * 修改用户权限
     * @requestbody
     * @return body
     */
    @RequiresRoles(value = {"SUPER_ADMIN"}, logical = Logical.OR)
    @PutMapping("/user/role")
    public  Object updateUserRole(@RequestBody Map<String, Object>  requestBody){
                String username = (String)requestBody.get("username");
                if (username == null) {
                    return BaseBody.fail("Username is Null");
                }
                Object role = requestBody.get("role_id");
                if (role == null){
                    return BaseBody.fail("Role_id is nul");
                }
                String str = role.toString();
                Long roleId = Long.valueOf(str);
                int Success = userService.updateUserRole(username,roleId);
                if (Success<1) {
                    return BaseBody.fail("Update fail");
                }
                BaseBody body = new BaseBody();
                body.setStatus(1);
                body.setMessage("Success");
                return body;
    }
    /**
     * 获取用户做题详细信息
     * @param username
     * @return Map<String,List<Map<String,Object>>>
     */
    @GetMapping("/user/count/problem")
    public Object getUserProblemCountDetails(@RequestParam  String username){

        UserPO userPO = userService.findByUserName(username);
        if (userPO == null) {
            return BaseBody.fail("USER DON'T EXISTS");
        }
        Long userId = userPO.getId();
        List<Map<String, Object>> ProblemTriedList = userService.getUserProblemTried(userId);
        List<Map<String, Object> > problemAcceptedList = userService.getUserProblemAccepted(userId);
        Map<String, Object> data = new HashMap<> ();
        BaseBody<Map<String, Object>> body = new BaseBody<> ();
        data.put("submit_problem_list",ProblemTriedList);
        data.put("accepted_problem_list",problemAcceptedList);
        body.setData(data);
        body.setStatus(1);
        body.setMessage("Success");
        return body;
    }
    /**
     * 获取用户信息接口
     * @param username
     * @return UserInfo
     */
    @GetMapping("/user/id/{username}")
    public Object getUserInfo (@PathVariable String username){
        UserInfo userInfo = userService.getUserInfo(username);
        if (userInfo == null) {
            return BaseBody.fail("USER DON'T EXISTS");
        }
        UserPO userPO = userService.findByUserName(username);
        Long userId = userPO.getId();

        Integer submitCount = userService.getUserSubmitCount(userId);
        //如果用户没有提交记录,则在solution表中查询不到数据，返回？？？
        if (submitCount==null){
            submitCount=0;
        }
        Integer acceptedCount = userService.getUserAcceptedCount(userId);
        if (acceptedCount == null)
        {acceptedCount=0;}
        Integer rank = userService.getUserRank(userId);
        if (rank == null) {
            //一道题都没有做 没有在排行榜上。 返回-1
            rank=-1;
        }
        userInfo.setRank(rank);
        userInfo.setAcceptedCount(acceptedCount);
        userInfo.setSubmitCount(submitCount);
        BaseBody<UserInfo> body = new BaseBody<>();
        body.setData(userInfo);
        body.setMessage("Success");
        body.setStatus(1);
        return body;
    }
    /**
     * 修改个人信息
     */
    @PutMapping("/user")
    @RequiresAuthentication
        public Object updateUserInfo (@RequestBody Map<String, Object> requestBody){
        Subject subject = SecurityUtils.getSubject();
        String token = (String)subject.getPrincipal();
        String username = JwtUtil.getSubject(token);
        String name = (String) requestBody.get("name");
        String description = (String) requestBody.get("description");
        String email = (String) requestBody.get("email");
        if (!isEmail(email))
            return BaseBody.fail("EMAIL ERROR");

        if (name!=null && name.length() > 10)
            return BaseBody.fail("NAME ERROR");
        if (description!=null && description.length() > 255)
            return BaseBody.fail("DESCRIPTION ERROR");
        int result = userService.updateUserInfo(username,name,email,description);
        if (result <1)
            return BaseBody.fail("UPDATE FAILURE");

        return BaseBody.success();
    }
    /**
     * 获取排名列表
     * @param page
     * @return List<Map<String,Object></>
     */
    @GetMapping("/rank")
    public Object getRankList(@RequestParam(defaultValue = "1") int page){
        List<Map<String, Object>> rankList = userService.getRank(page);

        for (int i =0;i<rankList.size(); i++){
            Map<String, Object> map = rankList.get(i);
            Long userId = (Long) map.get("userId");
            Integer submitCount = userService.getUserSubmitCount(userId);
            map.put("count",submitCount);
            Object rank = map.get("rank");
            String number  = rank.toString();
            char []numbers = number.toCharArray();
            String a =String.valueOf(numbers[0]) ;
            int Rank = Integer.parseInt(a);
            map.put("rank",Rank);
            map.remove("userId");
        }
        int count = rankList.size();
        Map<String,Object> data = new HashMap<String, Object> ();
        data.put("rank_list",rankList);
        data.put("count",count);

        BaseBody<Map<String, Object>> body = new BaseBody<> ();
        body.setData(data);
        body.setMessage("Success");
        body.setStatus(1);
        return body;

    }
    /**
     * 获取用户做题统计信息
     * @param username
     * @return Map<String,Object></>
     */
    @GetMapping("/user/count")
    public Object getUserCount(String username){
        if (username == null || username.length() == 0) {
            return BaseBody.fail("PARAM ERROR");
        }

        UserPO userPO = userService.findByUserName(username);
        if (userPO == null) {
            return BaseBody.fail("USER DON'T EXISTS");
        }
        Long userId = userPO.getId();

        Integer submitCount = userService.getUserSubmitCount(userId);
        //如果用户没有提交记录,则在solution表中查询不到数据，返回？？？
        if (submitCount==null){
            submitCount=0;
        }
        Integer acceptedCount = userService.getUserAcceptedCount(userId);
        if (acceptedCount == null)
        {acceptedCount=0;}
        Integer rank = userService.getUserRank(userId);
        if (rank == null) {
            //一道题都没有做 没有在排行榜上。 返回-1
            rank=-1;
        }
        Map<String, Object> data = new HashMap<>();
        data.put("submit_count",submitCount);
        data.put("accepted_count",acceptedCount);
        data.put("rank",rank);
        BaseBody<Map<String, Object>> body =new BaseBody();
        body.setData(data);
        body.setStatus(1);
        body.setMessage("Success");
        return body;

    }
    /**
     * excel批量增加用户
     * @file
     */
    @RequiresRoles(value = {"SUPER_ADMIN","ADMIN"}, logical = Logical.OR)
    @PostMapping("/user/excel")
    public Object insertUserByExcel(HttpServletRequest request,@RequestParam("file") MultipartFile file){
        List<UserExcelModel> list = new ArrayList<>();
        try {
            list = EasyExcel.read(file.getInputStream(),UserExcelModel.class,new ModelExcelListener()).sheet().doReadSync();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Map<String, Object>> userList = new ArrayList<> ();
        String ip = getIPAddress(request);
        for(int i =0;i<list.size();i++){
            Map<String, Object> map = new HashMap<> ();
            UserExcelModel userExcelModel = list.get(i);
            String name = userExcelModel.getName();
            String username = userExcelModel.getUsername();
            String password = userExcelModel.getPassword();
            map.put("name",name);
            map.put("username",username);
            UserPO userPO = userService.findByName(username);
            if (userPO !=null){
                return BaseBody.fail("No. "+i+" username is already exists");
            }
            String a = DigestUtils.md5DigestAsHex((username+password).getBytes());
            a = DigestUtils.md5DigestAsHex(("zkoj"+a).getBytes());
            //a 为前端加密值。
            if (!isLegal(username,a)){
                return BaseBody.fail("username or password is illegal");
            }
            String encodedPassword = EncodeUtil.encodePassword(password);
            map.put("password",encodedPassword);
            map.put("createIp",ip);
            map.put("lastIp",ip);
            userList.add(map);
        }
        userService.insertUsers(userList);

        BaseBody<List> body = new BaseBody();
        body.setMessage("Success");
        body.setStatus(1);
        return body;
    }
    /**
     * 批量增加用户
     * @requestbody
     * @return body
     */
    @RequiresRoles(value = {"SUPER_ADMIN"}, logical = Logical.OR)
    @PostMapping("/user")
    public Object insertUses(HttpServletRequest request,@RequestBody Map<String, Object> requestBody ){

        String ip = getIPAddress(request);
         List<Map<String, Object>> result =(List<Map<String, Object>>) requestBody.get("user_list");
        for(Map<String, Object> user: result ){
            String username = (String) user.get("username");
            UserPO userPO = userService.findByName(username);
            if (userPO !=null){
                return BaseBody.fail("This username is already exists");
            }
            String password = (String) user.get("password");
            if (!isLegal(username,password)){
                return BaseBody.fail("username or password is illegal");
            }
            String encodedPassword = EncodeUtil.encodePassword(password);

            user.put("password",encodedPassword);
            user.put("createIp",ip);
            user.put("lastIp",ip);
        }
        int isSuccess = userService.insertUsers(result);
        if (isSuccess <1) {
            return BaseBody.fail("fail");
        }
        BaseBody body = new BaseBody();
        body.setMessage("Success");
        body.setStatus(1);
        return body;
    }
    /**
     * 注册接口
     * */
    @PostMapping("/register")
    public  Object register(HttpServletRequest request, @RequestBody Map<String, Object> requestBody) {

        String username = (String) requestBody.get("username");
        String password = (String) requestBody.get("password");
        String name = (String) requestBody.get("name");

        // 非法数据
        if (!isLegal(username, password, name)) {
            return BaseBody.fail("ERROR PARAM");
        }
        // 用户名存在
        UserPO userPO = userService.findByUserName(username);
        if(userPO != null) {
            return BaseBody.fail("EXITS USERNAME");
        }

        UserPO newUserPO = new UserPO();
        newUserPO.setName(name);
        newUserPO.setUsername(username);
        newUserPO.setPassword(EncodeUtil.encodePassword(password));
        //检测登录用户的IP地址
        // TODO: 获取用户IP地址
        String ip = getIPAddress(request);
        newUserPO.setCreateIp(ip);
        //最后一次登录ip
        newUserPO.setLastIp(ip);
        //用户状态 默认为1
        newUserPO.setStatus((byte)1);

        //注册时间 在sql 语句中使用 now()函数进行赋值。

        //role_id  0 --super_admin 1 --normal 2 --admin
        newUserPO.setRoleId((byte)1);
        int isSuccess = userService.insert(newUserPO);
        if (isSuccess < 1){
            return BaseBody.fail("REGISTER INSERT ERROR");
        }
        // 新建完成后，创建一个jwt-token并返回给用户
        String jwtToken = userService.generateJwtToken(username);
        return ResponseEntity.ok().header("Authorization", "Bearer " + jwtToken)
                .body(BaseBody.success());

    }

    /**
     * 获取用户的ip地址
     * @param request -
     * @return String ip地址
     */
    protected String getIPAddress(HttpServletRequest request){
        String ipAddress;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            System.out.println(ipAddress);
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
                System.out.println(ipAddress);
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
                System.out.println(ipAddress);
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                System.out.println(ipAddress);
                if ("127.0.0.1".equals(ipAddress)) {
                    InetAddress inet = null;

                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                    System.out.println(ipAddress);
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            // "***.***.***.***".length()
            if (ipAddress != null && ipAddress.length() > 15) {
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                    System.out.println(ipAddress);
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }

        return ipAddress.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ipAddress;
    }

    /**
     * 判断传入的鉴权信息是否为合法数据
     * @param username 用户名
     * @param password 密码
     * @return 是否合法
     * */
    protected boolean isLegal(String username, String password) {

        // 数据为空
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return false;
        }
        // 长度不规范
        if(username.length() < 6 || username.length() > 18 || password.length() != 32) {
            return false;
        }
        // 格式不规范
        String username_regulation = "^[a-zA-Z0-9]+$";
        if(!username.matches(username_regulation)) {
            return false;
        }

        return true;
    }

    /**
     * 判断传入的鉴权信息是否为合法数据
     * @param username 用户名
     * @param password 密码
     * @param  name 名称
     * @return 是否合法
     * */
    protected boolean isLegal(String username, String password,String name) {
        // 数据为空
        if(StringUtils.isEmpty(name)) {
            return false;
        }
        // 长度不规范
        if(name.length() < 1 || name.length() > 10) {
            return false;
        }
        return isLegal(username, password);
    }

    /**
     * 判断传入的邮箱信息是否为合法数据
     * @param email 邮箱
     * @return 是否合法
     *
     */
    protected boolean isEmail(String email) {
        if (email==null)
            return true;
        Pattern p = Pattern.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.([a-zA-Z0-9_-])+)+$");  //正则表达式
        Matcher m = p.matcher(email);
        boolean b = m.matches();
        return b;
    }
    /**
     * 判断传入的昵称和描述是否为合法数据
     * @param name 昵称
     * @param description 描述
     * @return 是否合法
     *
     */
   /* protected boolean isLegal(String name,String description) {
            if (name.length()<1||name.length() > 10)
                return false;
            if (description.length() > 255|| description.length() <1)
                return false;
            return true;
    }*/
}
