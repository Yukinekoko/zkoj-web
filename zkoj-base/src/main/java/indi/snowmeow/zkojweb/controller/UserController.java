package indi.snowmeow.zkojweb.controller;

import indi.snowmeow.zkojweb.model.po.UserPO;
import indi.snowmeow.zkojweb.model.UserInfo;
import indi.snowmeow.zkojweb.model.req.UserRegisterRequest;
import indi.snowmeow.zkojweb.service.impl.UserServiceImpl;
import indi.snowmeow.zkojweb.util.BaseBody;
import indi.snowmeow.zkojweb.util.EncodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    //@PostMapping("/login")
    public Object login(HttpServletRequest request,
                        @RequestBody Map<String, Object> requestBody) {
/*        String username = (String) requestBody.get("username");
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

        User user = userService.findByUserName(username);
        Role role = userService.getRole(username);
        String name = user.getName();
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
                .body(responseBody);*/
        return null;
    }

    /**
     *  用户修改密码 普通用户
     *  TODO
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
/*    @PutMapping("/user")
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
    }*/

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
     * TODO
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
     * 注册接口
     * */
    @PostMapping("/register")
    public  Object register(HttpServletRequest request, @Valid @RequestBody UserRegisterRequest userRegisterRequest) {
        int result = userService.insert(userRegisterRequest, request);

/*        String username = (String) requestBody.get("username");
        String password = (String) requestBody.get("password");
        String name = (String) requestBody.get("name");

        // 非法数据
        if (!isLegal(username, password, name)) {
            return BaseBody.fail("ERROR PARAM");
        }
        // 用户名存在
        User user = userService.findByUserName(username);
        if(user != null) {
            return BaseBody.fail("EXITS USERNAME");
        }

        User newUser = new User();
        newUser.setName(name);
        newUser.setUsername(username);
        newUser.setPassword(EncodeUtil.encodePassword(password));
        //检测登录用户的IP地址
        // TODO: 获取用户IP地址
        String ip = getIPAddress(request);
        newUser.setCreateIp(ip);
        //最后一次登录ip
        newUser.setLastIp(ip);
        //用户状态 默认为1
        newUser.setStatus((byte)1);

        //注册时间 在sql 语句中使用 now()函数进行赋值。

        //role_id  0 --super_admin 1 --normal 2 --admin
        newUser.setRoleId((byte)1);
        int isSuccess = userService.insert(newUser);
        if (isSuccess < 1){
            return BaseBody.fail("REGISTER INSERT ERROR");
        }
        // 新建完成后，创建一个jwt-token并返回给用户
        String jwtToken = userService.generateJwtToken(username);
        return ResponseEntity.ok().header("Authorization", "Bearer " + jwtToken)
                .body(BaseBody.success());*/
        return null;
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
