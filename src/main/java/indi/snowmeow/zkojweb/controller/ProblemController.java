package indi.snowmeow.zkojweb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import indi.snowmeow.zkojweb.model.dto.*;
import indi.snowmeow.zkojweb.model.req.*;
import indi.snowmeow.zkojweb.model.*;
import indi.snowmeow.zkojweb.service.impl.ProblemServiceImpl;
import indi.snowmeow.zkojweb.service.impl.UserServiceImpl;
import indi.snowmeow.zkojweb.util.BaseBody;
import indi.snowmeow.zkojweb.util.SubjectUtils;
import indi.snowmeow.zkojweb.model.vo.ProblemDetailVO;
import indi.snowmeow.zkojweb.model.vo.ProblemListVO;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Long userId = userService.getIdFromUsername(SubjectUtils.getUsername());
        ProblemListRequestDTO requestDTO = new ProblemListRequestDTO();
        BeanUtils.copyProperties(request, requestDTO);
        requestDTO.setUserId(userId);
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

    /**
     *  修改问题内容
     */
    @RequiresRoles(value = {"SUPER_ADMIN","ADMIN"}, logical = Logical.OR)
    @PutMapping("/problem")
    public Object updateProblemInfo(@RequestBody ProblemUpdateRequest request) {
        ProblemUpdateDTO requestDTO = new ProblemUpdateDTO();
        BeanUtils.copyProperties(request, requestDTO);
        if(problemService.update(requestDTO)) {
            return BaseBody.success();
        }
        return BaseBody.fail("Unknown Error");
    }

    /**
     *  查询问题列表
     * @param  difficulty - 难度
     * @param  classId  - 分类id
     * @param  tagId    - 标签id
     * @param  uploadUsername -上传人用户名
     * @param  uploadUserId - 上传人uid
     * @param  sortType  - 排序类型
     * @param  search - 搜索关键字
     * @param  page - 页码 默认1
     * @param  limit - 每页数量 默认20
     * @return  List<Problem> - 问题列表 , count - 问题总数量 , 使用Map 包装传送到前端
     */
    @RequiresRoles(value = {"SUPER_ADMIN","ADMIN"}, logical = Logical.OR)
    @GetMapping("/iacs/problem")
    public Object adminGetProblemList(
            @RequestParam(name = "difficulty" , required = false) Byte difficulty,
            @RequestParam(name = "class_id" ,required = false)    Long classId,
            @RequestParam(name = "tag_id", required = false)      Long tagId,
            @RequestParam(name = "upload_username", required = false)String uploadUsername,
            @RequestParam(name = "upload_user_id" ,required = false)Long uploadUserId,
            @RequestParam(name = "sort_type",required = false)     String sortType,
            @RequestParam(name = "search",required = false)       String search,
            @RequestParam(name = "page" ,defaultValue = "1")      int page,
            @RequestParam(name = "limit",defaultValue = "20")     int limit
    ){
        BaseBody<Map<String, Object>> body = new BaseBody<> ();
        if (search!=null){
            List<Problem> problemList = problemService.getProblemBySearch(page, limit,search, sortType);
            int count  = problemService.countProblemBySearch(search);
            Map<String, Object> result = new HashMap<>();
            result.put("problem_list",problemList);
            result.put("count",count);
            body.setMessage("Success");
            body.setStatus(1);
            body.setData(result);
            return body;
        }
        else{
            List<Problem> problemList = problemService.getProblemByConditions(difficulty,classId,tagId,uploadUsername,uploadUserId,sortType,page,limit);
            int count = problemService.countProblemByConditions(difficulty, classId, tagId, uploadUsername, uploadUserId);
            Map<String, Object> resultByConditions = new HashMap<> ();
            resultByConditions.put("problem_list",problemList);
            resultByConditions.put("count",count);
            body.setMessage("Success");
            body.setStatus(1);
            body.setData(resultByConditions);
            return body;
        }

    }
    /**
     *  管理员增加问题 接口
     * @param request HttpServletRequest 使用multipart/form-data 上传文件以及数据 需要用HttpServletRequest来获取数据
     */
    @RequiresRoles(value = {"SUPER_ADMIN","ADMIN"}, logical = Logical.OR)
    @PostMapping("/iacs/problem")
    public Object insertProblem(HttpServletRequest request){

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        MultipartFile file = multipartRequest.getFile("check_point_file");
        String problemDataString = multipartRequest.getParameter("problem_data");
        String checkPointString = multipartRequest.getParameter("check_point_json");
        BaseBody body =new BaseBody();
        body = (BaseBody) problemService.insertProblem(file, problemDataString, checkPointString);
/*
        //1. 判断参数是否存在
        if (problemDataString == null) {
            // 没有题目数据
            return BaseBody.fail("Param Error.");
        }
        if (file== null || file.isEmpty()) {
            if (checkPointString == null) {
                // 没有测试数据
                return BaseBody.fail("No Check Point Data.");
            }
        }
        if(checkPointString != null) {
            // 同时提交两种测试数据
            return BaseBody.fail("Param Error.");
        }
        //2. 处理基本参数
        try {
            Problem problemData = new Problem();
            JsonNode problemDataNode = jsonMapper.readTree(problemDataString);
            String title = problemDataNode.get("title").asText();
            String description = problemDataNode.get("description").asText();
            String sampleInput = problemDataNode.get("sample_input").asText();
            String sampleOutput = problemDataNode.get("sample_output").asText();
            String hint = problemDataNode.get("hint").asText();
            Long classId = problemDataNode.get("problem_class").asLong(-1);
            if ( classId == -1) {
                classId = null;
            }
            List<Long> tagIdList = null;
            if(problemDataNode.get("tag").isArray()) {
                // 存在tag数组
                tagIdList = new ArrayList<>();
                for(JsonNode node : problemDataNode.get("tag")) {
                    Long id = node.asLong(-1);
                    if(id != -1) {
                        tagIdList.add(id);
                    }
                }
            }
            Boolean isPrivate = null;
            if(problemDataNode.get("is_private").isBoolean()) {
                isPrivate = problemDataNode.get("is_private").asBoolean();
            }


            //3.1 使用压缩包数据
            if (!file.isEmpty()) {

            }
            //3.2 使用Json数据
            if (checkPointString != null) {

            }

        } catch (JsonProcessingException e) {
            //Json转换错误 - 问题数据格式不正确
            e.printStackTrace();
        }
*/

        return body;
    }

}
