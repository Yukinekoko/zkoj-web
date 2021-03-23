package indi.snowmeow.zkojweb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import indi.snowmeow.zkojweb.model.dto.*;
import indi.snowmeow.zkojweb.model.req.*;
import indi.snowmeow.zkojweb.service.impl.ProblemServiceImpl;
import indi.snowmeow.zkojweb.service.impl.UserServiceImpl;
import indi.snowmeow.zkojweb.util.BaseBody;
import indi.snowmeow.zkojweb.model.vo.ProblemDetailVO;
import indi.snowmeow.zkojweb.model.vo.ProblemListVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/** 问题相关Controller
 * @author snowmeow
 * @date 2020/10/19
 */
@RestController
public class ProblemController {

    @Autowired
    ProblemServiceImpl problemService;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    ObjectMapper jsonMapper;

    /**
     * 获取文章列表
     * */
    @GetMapping("/problem")
    public Object getProblemList(@Valid @ModelAttribute ProblemListRequest request) {
        // TODO
        //Long userId = userService.getIdFromUsername(SubjectUtils.getUsername());
        ProblemListRequestDTO requestDTO = new ProblemListRequestDTO();
        BeanUtils.copyProperties(request, requestDTO);
        //requestDTO.setUserId(userId);
        ProblemListVO resultBody = problemService.list(requestDTO);
        return BaseBody.success(resultBody);
    }

    /**
     * 获取文章总数
     * */
    @GetMapping("/problem/count")
    public Object getProblemCount(@Valid @ModelAttribute ProblemCountRequest request) {
        ProblemCountDTO requestDTO = new ProblemCountDTO();
        BeanUtils.copyProperties(request, requestDTO);
        int count = problemService.countByPublic(requestDTO);
        return BaseBody.success(count);
    }

    /**
     * 获取问题详细
     * @param problemId 问题ID
     */
    @GetMapping("/problem/{problem_id}")
    public Object getProblemDetail(@PathVariable(name = "problem_id") Long problemId){
        ProblemDetailVO problem = problemService.getProblemDetail(problemId);
        return BaseBody.success(problem);
    }


}
