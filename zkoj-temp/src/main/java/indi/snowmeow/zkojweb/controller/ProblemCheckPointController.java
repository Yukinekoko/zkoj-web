package indi.snowmeow.zkojweb.controller;

import indi.snowmeow.zkojweb.model.Problem;
import indi.snowmeow.zkojweb.model.vo.ProblemCheckPointPreviewVO;
import indi.snowmeow.zkojweb.service.impl.ProblemCheckPointServiceImpl;
import indi.snowmeow.zkojweb.util.BaseBody;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author snowmeow
 * @date 2021/2/21
 */
@RestController
public class ProblemCheckPointController {

    @Autowired
    ProblemCheckPointServiceImpl problemCheckPointService;

    /**
     * 获取问题测试数据
     */
    @RequiresRoles(value = {"SUPER_ADMIN","ADMIN"}, logical = Logical.OR)
    @GetMapping("/problem/check-point/{problem_id}")
    public Object getCheckPoint(@PathVariable(name = "problem_id") Long problemId){
        List<ProblemCheckPointPreviewVO> checkPoints = problemCheckPointService.getFromProblemId(problemId);
        Map<String, Object> resultData = new HashMap<>();
        resultData.put("check_point_list", checkPoints);
        return BaseBody.success(resultData);
    }

    /**
     * 修改问题测试数据
     */
    @RequiresRoles(value = {"SUPER_ADMIN","ADMIN"}, logical = Logical.OR)
    @PutMapping("/problem/check-point")
    public Object updateProblemCheckPoint(@RequestParam Long problemId,
                                          @RequestParam(required = false) MultipartFile checkPointFile,
                                          @RequestParam(required = false) String checkPointJson){
        problemCheckPointService.update(problemId, checkPointFile, checkPointJson);
        return BaseBody.success();
    }
}
