package indi.snowmeow.zkojweb.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import indi.snowmeow.zkojweb.mapper.*;
import indi.snowmeow.zkojweb.messenger.MessageSender;
import indi.snowmeow.zkojweb.model.*;
import indi.snowmeow.zkojweb.service.SolutionService;
import indi.snowmeow.zkojweb.util.JmsResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author snowmeow
 * @date 2020/12/22
 */
@Service
@Transactional  //开启事务支持
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SolutionServiceImpl implements SolutionService {


    @Autowired
    UserMapper userMapper;
    @Autowired
    SolutionSourceCodeMapper solutionSourceCodeMapper;
    @Autowired
    SolutionMapper solutionMapper;
    @Autowired
    LanguageMapper languageMapper;
    @Autowired
    ProblemLimitMapper problemLimitMapper;
    @Autowired
    CheckPointMapper checkPointMapper;
    @Autowired
    MessageSender messageSender;

    @Override
    public Long createSolution(String username, Long problemId, Long languageId, String code) {

        User user = userMapper.findByUsername(username);
        if (user == null) {
            return null;
        }
        Long userId = user.getId();

        SolutionEntity solution = new SolutionEntity();
        solution.setProblemId(problemId);
        solution.setUserId(userId);
        // 初始时间与内存为0
        solution.setTime(0);
        solution.setMemory(0);
        // waiting
        solution.setStatusId((byte) 5);
        solution.setLanguageId(languageId);
        //创建源代码数据
        solutionMapper.insert(solution);
        long solutionId = solution.getId();
        System.out.println(">>>>>>> " + solutionId);
        SolutionSourceCode sourceCode = new SolutionSourceCode();
        sourceCode.setSolutionId(solutionId);
        sourceCode.setSourceCode(code);
        // 插入失败
        if (solutionSourceCodeMapper.insert(sourceCode) <= 0) {
            return null;
        }

        // 获取判题需要的一些信息
        // 1. 语言对象
        Language language = languageMapper.findById(languageId);
        /*// 2. 测试点
        List<CheckPoint> checkPointList = checkPointMapper.findListByProblemId(problemId);*/
        // 3. 限制信息
        ProblemLimit problemLimit = problemLimitMapper.
                findByProblemIdAndLanguageId(problemId,languageId);
        if(problemLimit == null) {
            problemLimit = problemLimitMapper.
                    findByProblemIdAndLanguageId(problemId, 0);
        }

        RequestSolution requestSolution = new RequestSolution();
        requestSolution.setId(solutionId);
        requestSolution.setProblemId(problemId);
        requestSolution.setSourceCode(code);
        requestSolution.setLanguage(language);
        requestSolution.setTime(problemLimit.getTime());
        requestSolution.setMemory(problemLimit.getMemory());

        try {
            String requestData = new ObjectMapper().writeValueAsString(JmsResultUtil.createSolution(requestSolution));
            messageSender.sendMessage("ZKOJ_SOLUTION_REQUEST", requestData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return solutionId;
    }

    @Override
    public List<Solution> getSolutionList(int page ,int limit, String userName, Long statusId, Long problemId,Long solutionId) {
        int offset = (page - 1) * limit;
        return solutionMapper.getSolutionList(offset,limit, userName, statusId, problemId,solutionId);
    }

    @Override
    public Map<String, Object> getSolutionDetail(Long solutionId, Boolean isUser) {
        return solutionMapper.getSolutionDetail(solutionId,isUser);
    }

    @Override
    public Long getUserIdFromSolutionId(Long solutionId) {
        return solutionMapper.getUserIdFromSolutionId(solutionId);
    }

    @Override
    public int getSolutionCount(Long problemId, Long statusId, String username ,Long solutionId) {
        return solutionMapper.getSolutionCount(problemId, statusId, username,solutionId);
    }
}
