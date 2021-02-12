package indi.snowmeow.zkojweb.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import indi.snowmeow.zkojweb.messenger.MessageSender;
import indi.snowmeow.zkojweb.model.Language;
import indi.snowmeow.zkojweb.model.Problem;
import indi.snowmeow.zkojweb.model.Solution;
import indi.snowmeow.zkojweb.service.UserService;
import indi.snowmeow.zkojweb.service.impl.SolutionServiceImpl;
import indi.snowmeow.zkojweb.service.impl.UserServiceImpl;
import indi.snowmeow.zkojweb.util.BaseBody;
import indi.snowmeow.zkojweb.util.JwtUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author snowmeow
 * @date 2021/01/26
 */
@RestController
public class SolutionController {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    SolutionServiceImpl solutionService;
    @Autowired
    MessageSender messageSender;
    @Autowired
    ObjectMapper jsonMapper;

    /**
     * 用户提交新的评测
     * Authentication
     * @param problemId - 问题ID
     * @param requestBody - {
     *                    code : 源码
     *                    language : 语言ID
     * }
     * */
    @RequiresAuthentication
    @PostMapping("/solution/problem/{problem_id}")
    public Object submitSolution(@PathVariable("problem_id") Long problemId,
                                 @RequestBody Map<String, Object> requestBody) {
        /**
         * 处理流程 事务操作
         * 1. 在数据表solution表中插入新的评测信息；并需要获取到solution_id
         * 2. 在数据表solution_source_code中插入对应的源代码
         * 3. 往评测机发送评测请求；
         * 4. 返回结果
         * */
        String code = (String) requestBody.get("code");
        Long language = (long) (Integer) requestBody.get("language_id");

        Subject subject = SecurityUtils.getSubject();
        String token = (String) subject.getPrincipal();
        String username = JwtUtil.getSubject(token);

        // 缺少数据
        if(StringUtils.isEmpty(code)) {
            return BaseBody.fail("ERROR PARAM");
        }

        Long solutionId = solutionService.createSolution(username, problemId, language, code);

        BaseBody<Map<String, Object>> result = new BaseBody<>();
        Map<String, Object> data = new HashMap<>();
        data.put("solution_id", solutionId);
        result.setStatus(1);
        result.setMessage("Success");
        result.setData(data);

        return result;
    }

    @GetMapping("/solution")
    public Object getSolutionList(@RequestParam(defaultValue = "1")int page,
                                  @RequestParam(defaultValue = "20")int limit,
                                  @RequestParam(value = "username", required = false) String username,
                                  @RequestParam(value = "status_id", required = false) Long statusId,
                                  @RequestParam(value = "problem_id", required = false) Long problemId,
                                  @RequestParam(value = "solution_id", required = false) Long solutionId
                                  ){

        if (page < 0 || limit > 100) {
            return BaseBody.fail("Param Error");
        }

        List<Solution> solutionList = solutionService.getSolutionList(page,limit, username, statusId, problemId,solutionId);
        int count = solutionService.getSolutionCount(problemId, statusId, username, solutionId);
        Map<String,Object> data = new HashMap<> ();
        data.put("solution_list",solutionList);
        data.put("count",count);
        BaseBody<Map<String, Object>> responseBody = new BaseBody<> ();
        responseBody.setStatus(1);
        responseBody.setMessage("Success");
        responseBody.setData(data);
        return responseBody;
    }
    /**
     *  获取评测详细
     * @param solutionId - 评测id
     * @return 评测详细信息
     */
    @GetMapping("/solution/{solution_id}")
    public Object getSolutionDetail(@PathVariable(name = "solution_id") Long solutionId){

        //TODO:有待优化 判断获取详细的用户跟评测提交用户为同一个，则返回源代码。否则不返回源代码。
        Boolean isUser = false;
        Subject subject = SecurityUtils.getSubject();
        Long userId= null;
        if (subject.isAuthenticated()) {
            String token = (String)subject.getPrincipal();
             userId = userService.findByUserName(JwtUtil.getSubject(token)).getId();
        }
        Long solutionUserId = solutionService.getUserIdFromSolutionId(solutionId);

        if (userId.equals(solutionUserId)){
            isUser=true;
        }
        Map<String, Object> solutionDetail = solutionService.getSolutionDetail(solutionId,isUser);
        BaseBody<Map<String, Object>> body = new BaseBody<> ();
        body.setStatus(1);
        body.setMessage("Success");
        body.setData(solutionDetail);
        return body;



    }
    /** 获取评测总数
     * @param problemId - 问题id
     * @param statusId - 状态id
     * @param username - 用户名
     * @return count 评测总数
     */
    public Object getSolutionCount(@RequestParam(name = "problem_id" , required = false)Long problemId,
                                   @RequestParam(name = "status_id" , required = false)Long statusId,
                                   @RequestParam(name = "username" , required = false)String username,
                                   @RequestParam (name = "solution_id" ,required = false) Long solutionId){

        int count = solutionService.getSolutionCount(problemId,statusId,username,solutionId);
        Map<String, Object> resultCount = new HashMap<> ();
        resultCount.put("count",count);
        BaseBody<Map<String, Object>> body = new BaseBody<> ();
        body.setStatus(1);
        body.setMessage("Success");
        body.setData(resultCount);
        return body;

    }
}
