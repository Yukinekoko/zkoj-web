package indi.snowmeow.zkojweb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import indi.snowmeow.zkojweb.dto.ProblemListRequestDTO;
import indi.snowmeow.zkojweb.req.ProblemListRequest;
import indi.snowmeow.zkojweb.model.*;
import indi.snowmeow.zkojweb.service.impl.ProblemServiceImpl;
import indi.snowmeow.zkojweb.service.impl.UserServiceImpl;
import indi.snowmeow.zkojweb.util.BaseBody;
import indi.snowmeow.zkojweb.util.JwtUtil;
import indi.snowmeow.zkojweb.util.SubjectUtils;
import indi.snowmeow.zkojweb.vo.ProblemDetailVO;
import indi.snowmeow.zkojweb.vo.ProblemListVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 问题相关Controller
 * @author snowmeow
 * @date 2020/10/19
 */
@Validated
@RestController
public class ProblemController {

    final static private Logger LOGGER = LoggerFactory.getLogger(ProblemController.class);
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
     * @param difficulty - 筛选难度
     * @param classId - 筛选分类
     * @param tagId - 筛选标签
     * */
    @GetMapping("/problem/count")
    public Object getProblemCount(@RequestParam(required = false) Byte difficulty,
                                  @RequestParam(name = "tag_id", required = false) Long tagId,
                                  @RequestParam(name = "class_id", required = false) Long classId) {

        if(difficulty != null && (difficulty > 3 || difficulty <= 0)) {
            return BaseBody.fail("Param Error");
        }

        int count = problemService.getCount(difficulty, tagId, classId, null);

        BaseBody<Integer> body = new BaseBody<>();
        body.setStatus(1);
        body.setMessage("Success");
        body.setData(count);

        return body;

    }
    /**
     * 获取语言列表
     * @return List<Language></>
     */
    @GetMapping("/language")
    public Object getLanguageList(){
        BaseBody<List<Language>> body  = new BaseBody<> ();
        List<Language> languages = problemService.getLanguageList();
        body.setData(languages);
        body.setMessage("Success");
        body.setStatus(1);
        return body;
    }
    /**
     * 获取问题详细
     * @param problemId 问题ID
     */
    @GetMapping("/problem/{problem_id}")
    public Object getProblemDetail(@PathVariable(name = "problem_id") Long problemId){
        ProblemDetailVO problem = problemService.getProblemDetail(problemId);
        BaseBody<ProblemDetailVO> body = new BaseBody<> ();
        body.setStatus(1);
        body.setMessage("Success");
        body.setData(problem);
        return body;
    }
    /**
     * 获取所有算法标签信息
     * @return List<ProblemTag></>
     */
        @GetMapping("/tag")
        public Object getTagList(){
            List<ProblemTag> problemTag = problemService.getTagList();
            BaseBody<List<ProblemTag>> body = new BaseBody<>();
            body.setStatus(1);
            body.setMessage("Success");
            body.setData(problemTag);
            return body;
        }

    /**
     *  获取所有分类信息
     * @return 分类信息 Map
      */
    @GetMapping("/class")
    public Object getClassesInfo(){
        List<Map<String, Object>> classInfoList = problemService.getClassesInfo();
        for (int i = 0;i < classInfoList.size(); i++) {
            Map<String, Object> classInfo = classInfoList.get(i);
            Object count = classInfo.get("count");
            String a = String.valueOf(count);
            // a = "[2]" 消除前后两个中括号
            String number = a.substring(1, a.length() - 1);
            //如果已经存在这个键值，会直接覆盖掉。
            classInfo.put("count", number);
        }
        BaseBody<List<Map<String, Object>>> body = new BaseBody<> ();
        body.setStatus(1);
        body.setMessage("Success");
        body.setData(classInfoList);
        return body;

    }
    /**
     *  修改问题内容
     * @param requestBody
     * @return body
     *
     */
    @RequiresRoles(value = {"SUPER_ADMIN","ADMIN"}, logical = Logical.OR)
    @PutMapping("/problem")
    public Object updateProblemInfo(@RequestBody Map<String, Object> requestBody) {

        BaseBody body = new BaseBody<>();
        Object id = requestBody.get("id");
        if (id == null) {
            return BaseBody.fail("PROBLEM ID IS NULL");
        }
        System.out.println(id);
        String idStr = id.toString();
        Long problemId = Long.parseLong(idStr);
        Problem problem = problemService.getProblemById(problemId);
        if (problem == null) {
            return BaseBody.fail("PROBLEM DON'T EXISTS");
        }

        String title = (String) requestBody.get("title");
        if (title != null) {
            if (title.length() > 40) {
                return BaseBody.fail("TITLE TOO LONG");
            }
        }
        Map<String, Object> problemMap = new HashMap<>();
        problemMap.put("id", problemId);
        problemMap.put("title", title);
        // problem.setTitle(title);
        String sampleInput = (String) requestBody.get("sample_input");
        problemMap.put("sampleInput", sampleInput);
        //  problem.setSampleInput(sampleInput);
        String sampleOutput = (String) requestBody.get("sample_output");
        problemMap.put("sampleOutput", sampleOutput);
        //  problem.setSampleOutput(sampleOutput);
        String description = (String) requestBody.get("description");
        problemMap.put("description", description);
        // problem.setDescription(description);
        String hint = (String) requestBody.get("hint");
        problemMap.put("hint", hint);
        // problem.setHint(hint);
        Boolean isPrivate = (Boolean) requestBody.get("is_private");
        problemMap.put("isPrivate", isPrivate);
        // problem.setPrivate(isPrivate);
        Object object = requestBody.get("difficulty");
        if (object != null) {
                String diffstr = object.toString();
                Byte difficulty = Byte.valueOf(diffstr);
                System.out.println(difficulty);
                problemMap.put("difficulty",difficulty);
    }
      //  problem.setDifficulty(difficulty);

        Object problemClassId = requestBody.get("problem_class");
        if (problemClassId != null) {
            String pcIdStr = problemClassId.toString();
            Long pcId = Long.parseLong(pcIdStr);
            ProblemClass problemClass = problemService.getClassFromId(pcId);
            if (problemClass == null) {
                return BaseBody.fail("PROBLEM CLASS DON'T EXISTS");
            }
            // problem.setProblemClass(problemClass);
            problemMap.put("classId", pcId);
        }
        if (description!=null|| //描述
                title!=null|| //题目
                sampleInput!=null|| //样例输入
                sampleOutput!=null|| // 样例输出
                hint!=null|| //hint
                problemClassId!=null|| //分组
                isPrivate!=null||//是否公开
                object!=null //难度
        ) {
            int isSuccess = problemService.updateProblemInfo(problemMap);
            if (isSuccess < 1) {
                return BaseBody.fail("UPDATE ERROR");
            }
        }
        //修改标签 采用 先把problem id 对应的标签全部删除再重新插入
        List<Integer> Tag =(List<Integer>) requestBody.get("tag");
        if (Tag!=null) {
            Long []tags = new Long[Tag.size()];
            int a = 0;
            for (Integer tag : Tag) {
                Long tagId = Long.valueOf(tag);
                tags[a++] = tagId;
                ProblemTag problemTag = problemService.getTagById(tagId);
                if (problemTag == null) {
                    return BaseBody.fail("PROBLEM TAG " + a + " DON'T EXISTS");
                }
            }
            int isSuccess = problemService.updateProblemTagList(tags,problemId);
            if (isSuccess<1){
                return BaseBody.fail("Problem Tag Insert Error");
            }
        }

        body.setStatus(1);
        body.setMessage("Success");
        return body;
    }

    /**
     * 删除算法标签
     * @requesbody
     * @return body
     */
    @RequiresRoles(value = {"SUPER_ADMIN","ADMIN"}, logical = Logical.OR)
    @DeleteMapping("/tag")
    public Object deleteProblemTag(@RequestBody Map<String, Object> requestBody ){

        Object id = requestBody.get("id");
        if (id == null) {
            return BaseBody.fail("TAG ID IS NULL");
        }
        System.out.println(id);
        String idStr = id.toString();
        Long tagId = Long.parseLong(idStr);
        ProblemTag problemTag = problemService.getTagById(tagId);
        if (problemTag == null) {
            return BaseBody.fail("This tag is not exists");
        }
        int isSuccess = problemService.deleteProblemTag(tagId);

        BaseBody body = new BaseBody();
        body.setMessage("Success");
        body.setStatus(1);
        return body;

    }
    /**
     * 删除分组
     * @requestbody
     * @return body
     *
     */
    @RequiresRoles(value = {"SUPER_ADMIN","ADMIN"}, logical = Logical.OR)
    @DeleteMapping("/problem-class")
    public Object deleteProblemClass(@RequestBody Map<String, Object> requestBody){
        Object id = requestBody.get("id");
        if (id == null) {
            return BaseBody.fail("CLASS ID IS NULL");
        }
        System.out.println(id);
        String idStr = id.toString();
        Long classId = Long.parseLong(idStr);
        ProblemClass problemClass = problemService.getClassFromId(classId);
        if (problemClass == null) {
            return BaseBody.fail("This Class is not exists");
        }
        int isSuccess = problemService.deleteProblemClass(classId);

        BaseBody body = new BaseBody();
        body.setMessage("Success");
        body.setStatus(1);
        return body;

    }
    /**
     * 获取问题测试数据
     * @requestbody
     * @return
     */
    @RequiresRoles(value = {"SUPER_ADMIN","ADMIN"}, logical = Logical.OR)
    @GetMapping("/problem/check-point/{problem_id}")
    public Object getCheckPoint(@PathVariable(name = "problem_id") Long problemId){
        Problem problem = problemService.getProblemById(problemId);
        if (problem==null)
            return BaseBody.fail("Problem don't exists");
        List<Map<String, Object>>  checkPointList = problemService.getProblemCheckPoint(problemId);
        Map<String, Object> data = new HashMap<> ();
        int count = checkPointList.size();
        data.put("check_point_list",checkPointList);
        data.put("count",count);
        BaseBody<Map<String, Object>> body = new BaseBody<> ();
        body.setData(data);
        body.setStatus(1);
        body.setMessage("Success");
        return body;

    }
    /**
     * 修改测试数据
     *
     */
    @RequiresRoles(value = {"SUPER_ADMIN","ADMIN"}, logical = Logical.OR)
    public Object updateCheckPointData(){
        //先删除所有的对应problemid的测试数据，重新插入新的。
        return null;

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
    public Object searchProblem(
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
    /**
     * 修改问题测试数据
     */
    @RequiresRoles(value = {"SUPER_ADMIN","ADMIN"}, logical = Logical.OR)
    @PutMapping("/problem/check-point")
    public Object updateProblemCheckPoint(HttpServletRequest request){

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Object obj = multipartRequest.getParameter("problem_id");
        if (obj == null) {
            return BaseBody.fail("Please submit problem id");
        }
        String str = obj.toString();
        Long problemId = Long.parseLong(str);
        Problem problem = problemService.getProblemById(problemId);
        if (problem == null) {
            return BaseBody.fail("Problem not exists");
        }
        MultipartFile file = multipartRequest.getFile("check_point_file");
        String checkPointString = multipartRequest.getParameter("check_point_json");
        BaseBody body = (BaseBody)problemService.updateProblemCheckPoint(problemId,file,checkPointString);

        return body;
    }

    /**
     * 管理员增加算法标签
     * @param requestBody -{name}
     */
    @RequiresRoles(value = {"SUPER_ADMIN","ADMIN"}, logical = Logical.OR)
    @PostMapping("/tag")
    public Object insertTag(@RequestBody Map<String, Object> requestBody){
        String name = (String) requestBody.get("name");
        if (name==null)
            return BaseBody.fail("PARAM ERROR");
        if (name.length()>40)
           return BaseBody.fail("PARAM TOO LONG");
        ProblemTag problemTag = new ProblemTag();
        problemTag.setName(name);
        ProblemTag tag = problemService.getTagByName(name);
        if (tag!=null) {
            return  BaseBody.fail("TAG ALREADY EXISTS");
        }
       int isSuccess =  problemService.insertTag(problemTag);
       if (isSuccess < 1) {
           return  BaseBody.fail("INSERT ERROR");
       }
        Long key = problemTag.getId();
        Map<String, Object> data = new HashMap<> ();
        data.put("id",key);
        BaseBody<Map<String, Object>> body = new BaseBody<> ();
        body.setStatus(1);
        body.setMessage("Success");
        body.setData(data);
        return body;
    }
    /**
     * 管理员修改算法标签
     * @param requestBody -{id,name}
     */
    @RequiresRoles(value = {"SUPER_ADMIN","ADMIN"}, logical = Logical.OR)
    @PutMapping("/tag")
    public Object updateProblemTag(@RequestBody Map<String, Object> requestBody){
        String name = (String) requestBody.get("name");
        String id = (String) requestBody.get("id");
        if (name==null||id == null)
            return BaseBody.fail("PARAM ERROR");
        if (name.length()>40)
            return BaseBody.fail("PARAM TOO LONG");
        Long ID = Long.parseLong(id);
        ProblemTag tag = problemService.getTagByName(name);
        if (tag!=null) {
            return  BaseBody.fail("TAG ALREADY EXISTS");
        }
        ProblemTag problemTag =new ProblemTag();
        problemTag.setId(ID);
        problemTag.setName(name);
        int isSuccess = problemService.updateProblemTag(problemTag);
        if (isSuccess < 1) {
            return BaseBody.fail("UPDATE ERROR");
        }
        BaseBody body = new BaseBody();
        body.setMessage("Success");
        body.setStatus(1);
        return body;

    }

    /**
     * 管理员修改分组信息
     * @param requestBody -{id,name,description}
     */
    @RequiresRoles(value = {"SUPER_ADMIN","ADMIN"}, logical = Logical.OR)
    @PutMapping("/problem-class")
    public Object updateProblemClass(@RequestBody Map<String, Object> requestBody){
        String name = (String) requestBody.get("name");
        String description = (String) requestBody.get("description");
        String id = (String) requestBody.get("id");
        if (name==null||description == null||id==null)
            return BaseBody.fail("PARAM ERROR");
        if (name.length()>40 ||description.length() > 255){
            return BaseBody.fail("PARAM TOO LONG");
        }
        Long ID = Long.parseLong(id);
        ProblemClass pc = problemService.getProblemClassByName(name);
        if (pc!=null){
            //判断其他名字相同的id 与这个id是否为相同
            if (!(pc.getId().equals(ID)))
            return  BaseBody.fail("CLASS NAME ALREADY EXISTS");
        }
        ProblemClass problemClass = new ProblemClass();
        problemClass.setName(name);
        problemClass.setId(ID);
        problemClass.setDescription(description);
        int isSuccess =  problemService.updateProblemClass(problemClass);
        if (isSuccess<1){
            return BaseBody.fail("UPDATE ERROR");
        }
        BaseBody body = new BaseBody();
        body.setMessage("Success");
        body.setStatus(1);
        return body;
    }

    /**
     * 管理员增加 problem_class 分组
     * @param requestBody -{name,description}
     *
     */
    @RequiresRoles(value = {"SUPER_ADMIN","ADMIN"}, logical = Logical.OR)
    @PostMapping("/problem-class")
    public Object insertProblemClass(@RequestBody Map<String, Object> requestBody){

        String name = (String) requestBody.get("name");
        String description = (String) requestBody.get("description");
        if (name==null||description == null)
            return BaseBody.fail("PARAM ERROR");
        if (name.length()>40 ||description.length() > 255){
            return BaseBody.fail("PARAM TOO LONG");
        }
        ProblemClass problemClass = new ProblemClass();
        problemClass.setName(name);
        problemClass.setDescription(description);
        ProblemClass pc = problemService.getProblemClassByName(name);
        if (pc!=null){
            return  BaseBody.fail("CLASS NAME ALREADY EXISTS");
        }
        int isSuccess =  problemService.insertProblemClass(problemClass);
        if (isSuccess<1){
            return BaseBody.fail("INSERT ERROR");
        }
        Long key = problemClass.getId();
        Map<String, Object> data = new HashMap<> ();
        data.put("id",key);
        BaseBody<Map<String, Object>> body = new BaseBody<> ();
        body.setStatus(1);
        body.setMessage("Success");
        body.setData(data);
        return body;
    }
}
