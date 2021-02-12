package indi.snowmeow.zkojweb.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import indi.snowmeow.zkojweb.exception.ParamErrorException;
import indi.snowmeow.zkojweb.mapper.*;
import indi.snowmeow.zkojweb.model.*;
import indi.snowmeow.zkojweb.po.ProblemClassPo;
import indi.snowmeow.zkojweb.po.ProblemLimitPo;
import indi.snowmeow.zkojweb.po.ProblemPo;
import indi.snowmeow.zkojweb.po.ProblemTagPo;
import indi.snowmeow.zkojweb.service.ProblemService;
import indi.snowmeow.zkojweb.util.BaseBody;
import indi.snowmeow.zkojweb.util.JwtUtil;
import indi.snowmeow.zkojweb.util.ListCopyUtil;
import indi.snowmeow.zkojweb.util.ZipFileUtil;
import indi.snowmeow.zkojweb.vo.ProblemClassPreviewVO;
import indi.snowmeow.zkojweb.vo.ProblemDetailVO;
import indi.snowmeow.zkojweb.vo.CurrentProblemLimitVO;
import indi.snowmeow.zkojweb.vo.ProblemTagVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author snowmeow
 * @date 2020/10/21
 */
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Service
public class ProblemServiceImpl implements ProblemService {


    @Autowired
    ProblemTagMapper problemTagMapper;
    @Autowired
    ProblemMapper problemMapper;
    @Autowired
    ProblemClassMapper problemClassMapper;
    @Autowired
    LanguageMapper languageMapper;
    @Autowired
    ObjectMapper jsonMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ProblemLimitMapper problemLimitMapper;
    @Autowired
    CheckPointMapper checkPointMapper;
    @Autowired
    SolutionMapper solutionMapper;

    @Override
    public List<Problem> getList(int page, int limit, Long userId, Byte difficulty, Long classId, Long tagId, String searchText) {

        int offset = (page - 1) * limit;

        if(searchText != null) {
            try {
                Integer.parseInt(searchText);
                return problemMapper.searchPreviewList(offset, limit, userId, searchText, true);
            } catch (NumberFormatException e) {
                return problemMapper.searchPreviewList(offset, limit, userId, searchText, false);
            }
        }

        return problemMapper.getList(offset, limit, userId, difficulty, classId, tagId);
    }

    @Override
    public int getCount(Byte difficulty, Long tagId, Long classId, String searchText) {
        if(searchText != null) {
            try {
                Integer.parseInt(searchText);
                return problemMapper.getProblemCount(difficulty, tagId, classId, searchText, true);
            } catch (NumberFormatException e) {
                return problemMapper.getProblemCount(difficulty, tagId, classId, searchText, false);
            }
        }
        return problemMapper.getProblemCount(difficulty, tagId, classId, null, null);
    }

    @Override
    public ProblemDetailVO getProblemDetail(long problemId) {
        ProblemDetailVO result = new ProblemDetailVO();

        ProblemPo problem = problemMapper.getProblemDetail(problemId);
        BeanUtils.copyProperties(problem, result);

        int submitCount = solutionMapper.countSubmitFromProblemId(problemId);
        int acceptedCount = solutionMapper.countSubmitAcceptedCountFromProblemId(problemId);
        result.setCount(submitCount);
        result.setAccepted(acceptedCount);

        if(problem.getClassId() != null) {
            ProblemClassPo problemClass = problemClassMapper.getFromId(problem.getClassId());
            ProblemClassPreviewVO problemClassPreviewVO = new ProblemClassPreviewVO();
            BeanUtils.copyProperties(problemClass, problemClassPreviewVO);
            result.setProblemClass(problemClassPreviewVO);
        }

        List<ProblemTagPo> problemTags = problemTagMapper.getListFromProblemId(problemId);
        if(problemTags.size() > 0) {
            List<ProblemTagVO> problemTagVOs = ListCopyUtil.copy(problemTags, ProblemTagVO.class);
            result.setTag(problemTagVOs);
        }

        List<ProblemLimitPo> problemLimits = problemLimitMapper.getListByProblemId(problemId);
        if(problemLimits.size() > 0) {
            List<CurrentProblemLimitVO> currentProblemLimitVOS =
                    ListCopyUtil.copy(problemLimits, CurrentProblemLimitVO.class);
            result.setLimit(currentProblemLimitVOS);
        }

        return result;
    }

    @Override
    public List<ProblemTag> getTagList() {
        return problemTagMapper.getTagList();
    }

    @Override
    public List<Map<String, Object>> getClassesInfo() {
        return problemClassMapper.getClassesInfo();
    }

    @Override
    public List<Problem> getProblemBySearch(Integer page, Integer limit, String search, String sortType) {
        int offset = (page - 1) * limit;
        //默认为false
         Boolean isNumber =false;
        //判断 search 是否为纯数字
        for (int i = 0; i < search.length(); i++) {
            int chr = search.charAt(i);
            if (chr>48&&chr<57)
                isNumber=true;
            else {
                //存在非数字 属于搜索题目范围
                isNumber = false;
                break;
            }
        }
        List<Problem> problemList = problemMapper.getProblemBySearch(offset,limit, search, isNumber);
        if (problemList.isEmpty()){
            //如果题号查询不到，则在题目中查询。
            isNumber=false;
            problemList = problemMapper.getProblemBySearch(offset,limit, search, isNumber);
        }
        //实现排序
        problemList = sort(problemList,sortType);
        return problemList;
    }

    @Override
    public List<Problem> getProblemByConditions(Byte difficulty, Long classId, Long tagId, String uploadUsername, Long uploadUserId, String sortType,Integer page, Integer limit) {
        int offset = (page - 1) * limit;
        //如果存在userId 则把 username 为null
        if (uploadUserId!=null)
            uploadUsername=null;
        List<Problem> problemList = problemMapper.getProblemByConditions(difficulty,classId,tagId,uploadUsername,uploadUserId,offset,limit);
        problemList = sort(problemList,sortType);
        return problemList;
    }
    /**
     *  新增算法标签
     * @param problemTag - 算法标签对象
     * @return 数据影响行数
     */
    @Override
    public int insertTag(ProblemTag problemTag) {
        return problemTagMapper.insertTag(problemTag);
    }
    /**
     *  新增问题分组
     * @param problemClass - 问题分组对象
     * @return 数据影响行数
     */
    @Override
    public int insertProblemClass(ProblemClass problemClass) {
        return problemClassMapper.insertProblemClass(problemClass);
    }

    @Override
    public ProblemTag getTagByName(String name) {
        return problemTagMapper.getTagByName(name);
    }

    @Override
    public ProblemClass getProblemClassByName(String name) {
        return problemClassMapper.getProblemClassByName(name);
    }

    /**
     * 修改分组信息
     * @param problemClass
     */
    @Override
    public int updateProblemClass(ProblemClass problemClass) {
        return problemClassMapper.updateProblemClass(problemClass);
    }
    /**
     *
     * 修改算法标签信息
     * @param problemTag
     */
    @Override
    public int updateProblemTag(ProblemTag problemTag) {
        return problemTagMapper.updateProblemTag(problemTag);
    }

    /**
     * 增加问题
     * @param problem
     * @return 数据影响行数
    */
    @Override
    public int insertProblem(Problem problem) {
        return problemMapper.insertProblem(problem);
    }
    /**
     * 根据id获取指定分类对象
     * @param id - class_id
     * @return 指定分类对象
     * */
    @Override
    public ProblemClass getClassFromId(long id) {
        return problemClassMapper.getClassFromId(id);
    }
    /**
     * 根据id 获取指定标签对象
     * @param id - tag_id
     * @return 指定标签对象
     */
    @Override
    public ProblemTag getTagById(Long id) {
        return problemTagMapper.getTagById(id);
    }

    @Override
    public List<Language> getLanguageList() {
        return languageMapper.getLanguageList();
    }

    @Override
    @Transactional()
    public Object insertProblem(MultipartFile file, String problemDataString, String checkPointString) {

        if(problemDataString == null) {
            // 参数错误
            // 没有题目数据
            throw new ParamErrorException("Param Error.");
        }
        if((file==null||file.isEmpty()) && checkPointString == null) {
            // 参数错误
            // 没有测试用例数据
            throw new ParamErrorException("No Check Point Data.");
        }

        if(file!=null && checkPointString != null) {
            // 参数错误
            // 提交两种测试数据
            throw new ParamErrorException("Param Error.");
        }
        BaseBody<Map<String, Object>> body = new BaseBody<> ();
        Map<String, Object> problemDataMap = null;
        //   jsonMapper.
        //使用 json 提交测试数据

            Map<String, Object> checkPointMap = null;
            try {
                problemDataMap = jsonMapper.readValue(problemDataString, Map.class);
                //   checkPointMap  = jsonMapper.readValue(checkPointString, Map.class);
                //获取 title sampleoutput isprivate
                String title = (String) problemDataMap.get("title");
                String sampleOutput = (String) problemDataMap.get("sample_output");
                Boolean isPrivate = (Boolean) problemDataMap.get("is_private");
                //获取源代码
                List<Map<String, Object>> sourceCode = (List<Map<String, Object>>) problemDataMap.get("source_code");

                if (title == null || sampleOutput == null || isPrivate == null ) {
                    throw new ParamErrorException("Param Error.");
                }
                if (title.length()>40)
                    throw new ParamErrorException("Title Too Long.");
                //获取 描述 数据库 text类型
                String description = (String) problemDataMap.get("description");
                if (description == null || description.length() == 0) {
                    throw new ParamErrorException("Param Error.");
                }
                //可以为null 获取到的结果为 "" 没有字符 非null
                // 数据库中 类型为text 一般text 我都不判断超范围
                String sampleInput = (String) problemDataMap.get("sample_input");
                String hint = (String) problemDataMap.get("hint");
                //获取 problemClass 可以为null
                Integer classIdInteger = ((Integer) problemDataMap.get("problem_class"));
                ProblemClass problemClass = new ProblemClass();
                if (classIdInteger != null){
                    Long classId = classIdInteger.longValue();
                    problemClass = problemClassMapper.getClassFromId(classId);
                    //分组不存在
                    if (problemClass==null){
                        throw new ParamErrorException("Class Not Exists.");
                    }
                }
                else{
                    System.out.println(problemClass.getName());
                    System.out.println("PROBLEM CLASS NULL");
                }
                //获取tag list 可以为null
                List<Integer> tagIdList = (List<Integer>)problemDataMap.get("tag");
                List<ProblemTag> problemTagList = new ArrayList<ProblemTag>();
                if (tagIdList != null) {
                    int i =0;
                    for (Integer tagId : tagIdList) {
                        i++;
                        ProblemTag problemTag = new ProblemTag();
                        Long tagid = tagId.longValue();
                        problemTag = problemTagMapper.getTagById(tagid);
                        if (problemTag==null){
                            //标签不存在
                            throw new ParamErrorException("Tag " + i + "Not Exists");
                        }
                        problemTagList.add(problemTag);
                    }
                }else {
                    System.out.println(problemTagList.size());
                    System.out.println("PROBLEM Tag List NULL");
                }
                //获取 difficulty 没有传入difficulty 默认为1

                Integer a  =(Integer)  problemDataMap.get("difficulty");
                Byte difficulty = a.byteValue();
                if (difficulty == null||difficulty==0) {
                    difficulty=1;
                }
                // 获取 user
                User user =new User();
                Subject subject = SecurityUtils.getSubject();
                if (subject.isAuthenticated()) {
                    String token = (String)subject.getPrincipal();
                     user = userMapper.findByUsername(JwtUtil.getSubject(token));
                }
                //获取limit
                List<Map<String, Object>> limitList =(List<Map<String, Object>>) problemDataMap.get("limit");
                //limit 为必须 没有则返回错误信息
                if (limitList==null || limitList.size() == 0){
                    throw new ParamErrorException("Param Error.");
                }
                //存放 从json 获取到的problemlimit对象
                List<ProblemLimit> problemLimitList = new ArrayList<> ();
                for (int i =0;i<limitList.size(); i++) {
                    Map<String, Object> limit = limitList.get(i);
                    Integer b  = (Integer) limit.get("language_id");
                    Long languageId = b.longValue();
                    Language language = languageMapper.findById(languageId);
                    if (language == null) {
                        throw new ParamErrorException("Language " + i + "Not Exists");
                    }
                    Integer memory = (Integer) limit.get("memory");
                    Integer time = (Integer) limit.get("time");
                    if (memory<0||time<0){
                        throw new ParamErrorException("Param Error.");
                    }
                    ProblemLimit problemLimit =new ProblemLimit();
                    problemLimit.setLanguageId(languageId);
                    problemLimit.setMemory(memory);
                    problemLimit.setTime(time);
                    problemLimitList.add(problemLimit);
                }
                Problem newProblem = new Problem();
                newProblem.setTitle(title);
                newProblem.setDescription(description);
                newProblem.setSampleInput(sampleInput);
                newProblem.setSampleOutput(sampleOutput);
                newProblem.setHint(hint);
                newProblem.setDifficulty(difficulty);
                newProblem.setProblemClass(problemClass);
                newProblem.setUser(user);
                newProblem.setPrivate(isPrivate);
                int isSuccess = problemMapper.insertProblem(newProblem);
                if (isSuccess < 1) {
                    throw new ParamErrorException("Unknown Error.");
                }

                Long problemId = newProblem.getId();
                //对tag 插入 problem_tag_mapping
                if (problemTagList!=null){
                    //有传参数回来
                    for (int i = 0; i < problemTagList.size(); i++) {
                       Long tagId =  problemTagList.get(i).getId();
                       //插入数据库
                        int PTisSuccess = problemTagMapper.insertProblemTag(problemId, tagId);
                        if (PTisSuccess<1){
                            throw new ParamErrorException("Unknown Error.");
                        }
                    }
                }
                //对limit 插入 problem_limit
              //  Long [] languages = new Long[problemLimitList.size()];
                if (problemLimitList!=null){
                    //必须的
                    for (int i = 0; i < problemLimitList.size(); i++) {
                        ProblemLimit pl = problemLimitList.get(i);
                        //已经对变量进行检查 语言id是存在的
                        Long languageId = pl.getLanguageId();
                      //  languages[i]=languageId;
                        Integer memory = pl.getMemory();
                        Integer time = pl.getTime();
                        int PLisSuccess =problemLimitMapper.insertProblemLimit(problemId,languageId,memory,time);
                        if (PLisSuccess <1){
                            throw new ParamErrorException("Unknown Error.");
                        }
                    }
                }
                //对source_code 查询语言id 有没有
                if (sourceCode!=null){
                    //创建map 获取 list 中的map对象
                    for (int i = 0; i < sourceCode.size(); i++) {
                        Map<String, Object> sc = sourceCode.get(i);
                        Integer c = (Integer) sc.get("language_id");
                        Long languageId  = c.longValue();
                        Language language = languageMapper.findById(languageId);
                        if (language == null) {
                            throw new ParamErrorException("Language " + i + " Not Exists");
                        }
                    /*    for (int j =0;j<languages.length;j++){
                            //与上段 limit 传过来的language id 对应 如果存在不一样的 则报错
                            if (languages[j].equals(languageId)){
                                break;
                            }
                            if (j==languages.length-1){
                                throw new ParamErrorException("Param Error.");
                            }
                        } */
                        String code = (String) sc.get("source_code");
                        if (code == null){throw new ParamErrorException("Param Error.");}
                        int SCisSuccess =problemMapper.insertSourceCode(problemId,code,languageId);
                        if (SCisSuccess <1 ){
                            throw new ParamErrorException("Unknown Error.");
                        }
                    }
                }

                Map<String, Object> data = new HashMap<>();
                data.put("problemId",problemId);
                body.setData(data);
                //开始对每一个样例输入输出进行处理 分别对 json 和 压缩包进行处理
                if(checkPointString != null&& file==null) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    List<Map<String, Object>> sampleList = objectMapper.readValue(checkPointString,new TypeReference<List<Map<String, Object>> >() {});
                    int i =1;
                    for (Map<String, Object> samples : sampleList) {
                        String input =(String) samples.get("input");
                        String output = (String) samples.get("output");
                        System.out.println("input : "+ input+"\n"+"output : "+output);
                        //input 可以为空 output不可以为空
                        if (output==null){
                            throw new ParamErrorException("Output" + i + "Is Null");
                        }
                        i++;
                        int PUTisSuccess = checkPointMapper.insertCheckPoint(problemId,input, output);
                    }

                }
                // 到这一步 通过json 返回的 题目信息和输入输出信息已经全部插入数据库 。
                // 接下来是以压缩包形式返回后端的输入输出信息
                // 如果压缩包不是null 则json范围的输入输出信息必定为null
                if (file!=null && checkPointString==null){
                    if (file.isEmpty()){
                        throw new ParamErrorException("压缩包为空");
                    }
                    File f = null;
                    try {
                        f=File.createTempFile("tmp", null);
                        file.transferTo(f);
                    }
                     catch (IOException e) {
                        f.delete();
                        throw new ParamErrorException("Check Point File Error.");
                    }
                    ZipFileUtil zipFile = new ZipFileUtil(f);
                    if (!zipFile.isZip()){
                        f.delete();
                        throw new ParamErrorException("Check Point File Is Not A ZIP.");
                    }
                    if (zipFile.isEncrypted()){
                        f.delete();
                        throw new ParamErrorException("Check Point File Error.");
                    }
                    if (zipFile.isLegal()){
                        Map<String, String> files= zipFile.readFiles();
                        ArrayList<String> inputNames = zipFile.getInputNames();
                        ArrayList<String> outputNames = zipFile.getOutputNames();
                        String input = "";
                        String output = "";
                        for (int j = 0; j < inputNames.size(); j++) {
                            String inputName = inputNames.get(j);
                            //检验输入输出的文件数字是否匹配 input_1 对 output_1
                            String inputNumber = inputName.substring(6,inputName.length() );
                            for (int i = 0; i < outputNames.size(); i++){
                                String name = outputNames.get(i);
                                String number = name.substring(7,name.length() );
                                if (number.equals(inputNumber)){
                                    input = files.get(inputName);
                                    System.out.println(inputName+"："+input);
                                    output = files.get(name);
                                    System.out.println(name +"："+output);
                                    int PUTisSuccess = checkPointMapper.insertCheckPoint(problemId,input,output);
                                    if (PUTisSuccess<1){
                                        f.delete();
                                        throw new ParamErrorException("Unknown Error.");
                                    }
                                    outputNames.remove(i);
                                    break;
                                }
                            }
                        }
                        for (int i =0;i<outputNames.size(); i++){
                            //Sql 插入数据库
                            output = files.get(outputNames.get(i));
                            int PUTisSuccess = checkPointMapper.insertCheckPoint(problemId,input,output);
                            if (PUTisSuccess<1){
                                f.delete();
                                throw new ParamErrorException("Unknown Error.");
                            }
                            System.out.println(outputNames.get(i));
                        }

                    }
                    f.delete();
                }

                body.setMessage("Success");
                body.setStatus(1);

                return body;
        } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

      return body;
    }

    @Override
    public int insertProblemTag(Long problemId, Long tagId) {
        return problemTagMapper.insertProblemTag(problemId, tagId);
    }

    @Override
    public int insertProblemLimit(Long problemId, Long languageId, Integer memory, Integer time) {
        return problemLimitMapper.insertProblemLimit(problemId, languageId, memory,time);
    }

    @Override
    public int insertSourceCode(Long problemId, String sourceCode, Long languageId) {
        return problemMapper.insertSourceCode(problemId, sourceCode, languageId);
    }

    @Override
    public int insertCheckPoint(Long problemId, String input, String output) {
        return checkPointMapper.insertCheckPoint(problemId, input, output);
    }

    @Override
    public Problem getProblemById(Long problemId) {
        return problemMapper.getProblemById(problemId);
    }

    @Override
    public int updateProblemTagList(Long[] tagIds, Long problemId) {
        //执行删除
        int delete = problemTagMapper.deleteProblemTagList(problemId);

        int insert=0;
        for (Long tagId : tagIds) {
            insert = problemTagMapper.insertProblemTagList(tagId,problemId);
            if (insert<1){return -1;}
        }


        return insert;
    }

    @Override
    public int updateProblemInfo(Map<String, Object> problemMap) {
        return problemMapper.updateProblemInfo(problemMap);
    }

    @Override
    public int deleteProblemTag(Long tagId) {
        return problemTagMapper.deleteProblemTag(tagId);
    }

    @Override
    public int deleteProblemClass(Long classId) {
        return problemClassMapper.deleteProblemClass(classId);
    }

    @Override
    @Transactional()
    public Object updateProblemCheckPoint(Long problemId, MultipartFile file, String checkPointString) {

        if (file == null &&checkPointString==null)
            return BaseBody.fail("PLEASE SUBMIT CHECK POINT DATA");

        if (file!=null && checkPointString !=null)
            return BaseBody.fail("SELECT ONLY ONE KIND OF DATA TO SUBMIT");
        BaseBody body = new BaseBody();


        //执行对problem 的checkpoint 删除
        checkPointMapper.deleteCheckPoint(problemId);
        //zip 压缩包格式上传
        if (file != null  ){
            if (file.isEmpty()){
                return BaseBody.fail("ZIP IS EMPTY");
            }
            //再执行插入
            File f = null;
            try {
                f=File.createTempFile("tmp", null);
                file.transferTo(f);
            }
            catch (IOException e) {
                f.delete();
                throw new ParamErrorException("Check Point File Error.");
            }
            ZipFileUtil zipFile = new ZipFileUtil(f);
            if (!zipFile.isZip()){
                f.delete();
                throw new ParamErrorException("Check Point File Is Not A ZIP.");
            }
            if (zipFile.isEncrypted()){
                f.delete();
                throw new ParamErrorException("Check Point File Error.");
            }
            if (zipFile.isLegal()){
                Map<String, String> files= zipFile.readFiles();
                ArrayList<String> inputNames = zipFile.getInputNames();
                ArrayList<String> outputNames = zipFile.getOutputNames();
                String input = "";
                String output = "";
                for (int j = 0; j < inputNames.size(); j++) {
                    String inputName = inputNames.get(j);
                    //检验输入输出的文件数字是否匹配 input_1 对 output_1
                    String inputNumber = inputName.substring(6,inputName.length() );
                    for (int i = 0; i < outputNames.size(); i++){
                        String name = outputNames.get(i);
                        String number = name.substring(7,name.length() );
                        if (number.equals(inputNumber)){
                            input = files.get(inputName);
                            System.out.println(inputName+"："+input);
                            output = files.get(name);
                            System.out.println(name +"："+output);
                            int PUTisSuccess = checkPointMapper.insertCheckPoint(problemId,input,output);
                            if (PUTisSuccess<1){
                                f.delete();
                                throw new ParamErrorException("Unknown Error.");
                            }
                            outputNames.remove(i);
                            break;
                        }
                    }
                }
                for (int i =0;i<outputNames.size(); i++){
                    //Sql 插入数据库
                    output = files.get(outputNames.get(i));
                    int PUTisSuccess = checkPointMapper.insertCheckPoint(problemId,input,output);
                    if (PUTisSuccess<1){
                        f.delete();
                        throw new ParamErrorException("Unknown Error.");
                    }
                    System.out.println(outputNames.get(i));
                }

            }
            f.delete();
            body.setStatus(1);
            body.setMessage("Success");
            return body;
        }
        //对json格式的更新
        if (checkPointString !=null) {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, Object>> sampleList = null;
            try {
                sampleList = objectMapper.readValue(checkPointString, new TypeReference<List<Map<String, Object>>>() {
                });
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            int i = 1;
            for (Map<String, Object> samples : sampleList) {
                String input = (String) samples.get("input");
                String output = (String) samples.get("output");
                System.out.println("input : " + input + "\n" + "output : " + output);
                //input 可以为空 output不可以为空
                if (output == null) {
                    throw new ParamErrorException("Output" + i + "Is Null");
                }
                i++;
                checkPointMapper.insertCheckPoint(problemId, input, output);
            }
            body.setStatus(1);
            body.setMessage("Success");
            return body;
        }

        return BaseBody.fail("Fail");
    }

    @Override
    public int countProblemByConditions(Byte difficulty, Long classId, Long tagId, String uploadUsername, Long uploadUserId) {
        if (uploadUserId!=null)
            uploadUsername=null;
        return problemMapper.countProblemByConditions(difficulty, classId,tagId, uploadUsername, uploadUserId);
    }

    @Override
    public int countProblemBySearch(String search) {
        Boolean isNumber =false;
        //判断 search 是否为纯数字
        for (int i = 0; i < search.length(); i++) {
            int chr = search.charAt(i);
            if (chr>48&&chr<57)
                isNumber=true;
            else {
                //存在非数字 属于搜索题目范围
                isNumber = false;
                break;
            }
        }
        int problemList = problemMapper.countProblemBySearch( search, isNumber);
        if (problemList==0){
            //如果题号查询不到，则在题目中查询。
            isNumber=false;
            problemList = problemMapper.countProblemBySearch(search, isNumber);
        }

        return problemList;
    }

    @Override
    public List<Map<String, Object>> getProblemCheckPoint(Long problemId) {
        return checkPointMapper.getProblemCheckPoint(problemId);
    }


    /**
     *  查询问题列表的排序方法
     */
    public List<Problem> sort(List<Problem> problemList ,String sortType){
        if (sortType == null)
            return problemList;
        if (sortType.equals("count")){
            System.out.println("sort:count");
            problemList.sort((x,y)->Integer.compare(y.getCount(),x.getCount()));
            return problemList;
        }
        if (sortType.equals("count_r")) {
            System.out.println("sort:count_r");
            problemList.sort((x,y)->Integer.compare(x.getCount(),y.getCount()));
            return problemList;
        }
        if (sortType.equals("accepted")){
            System.out.println("sort:accepted");
            problemList.sort((x,y)->Integer.compare(y.getAccepted(),x.getAccepted()));
            return problemList;
        }
        if (sortType.equals("accepted_r")){
            System.out.println("sort:accepted_r");
            problemList.sort((x,y)->Integer.compare(x.getAccepted(),y.getAccepted()));
            return problemList;
        }
        if (sortType.equals("acceptability")){
            System.out.println("sort:acceptability");
            problemList.sort((x,y)->Double.compare(y.returnAcceptability(),x.returnAcceptability()));
            return problemList;
        }
        if (sortType.equals("acceptability_r")){
            System.out.println("sort:acceptability_r");
            problemList.sort((x,y)->Double.compare(x.returnAcceptability(),y.returnAcceptability()));
            return problemList;
        }
        return problemList;
    }
}
