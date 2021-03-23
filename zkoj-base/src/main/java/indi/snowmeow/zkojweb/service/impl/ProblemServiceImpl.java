package indi.snowmeow.zkojweb.service.impl;

import com.alibaba.druid.util.StringUtils;
import indi.snowmeow.zkojweb.model.dto.*;
import indi.snowmeow.zkojweb.mapper.*;
import indi.snowmeow.zkojweb.model.po.ProblemClassPO;
import indi.snowmeow.zkojweb.model.po.ProblemLimitPO;
import indi.snowmeow.zkojweb.model.po.ProblemPO;
import indi.snowmeow.zkojweb.model.po.ProblemTagPO;
import indi.snowmeow.zkojweb.service.ProblemService;
import indi.snowmeow.zkojweb.util.ListCopyUtil;
import indi.snowmeow.zkojweb.model.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * @author snowmeow
 * @date 2020/10/21
 */
//@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Service
public class ProblemServiceImpl implements ProblemService {


    @Autowired
    ProblemTagMapper problemTagMapper;
    @Autowired
    ProblemMapper problemMapper;
    @Autowired
    ProblemClassMapper problemClassMapper;
    @Autowired
    ProblemLimitMapper problemLimitMapper;
    @Autowired
    SolutionMapper solutionMapper;
    @Autowired
    ProblemTagMappingMapper problemTagMappingMapper;

    @Override
    public ProblemListVO list(ProblemListRequestDTO requestDTO) {
        ProblemListVO result = new ProblemListVO();

        int offset = (requestDTO.getPage() - 1) * requestDTO.getLimit();
        requestDTO.setOffset(offset);

        String searchText = requestDTO.getSearchText();
        Boolean isSearchId = null;
        List<ProblemPO> problemPOs;

        if (null != searchText) {
            if(StringUtils.isNumber(searchText)) {
                isSearchId = true;
                problemPOs = problemMapper.listSearchFromId(Long.parseLong(searchText), offset, requestDTO.getLimit());
            } else {
                isSearchId = false;
                problemPOs = problemMapper.listSearchFromName(searchText, offset, requestDTO.getLimit());
            }
        } else {
            problemPOs = problemMapper.list(requestDTO);
        }

        List<ProblemPreviewVO> resultProblemVOs = ListCopyUtil.copy(problemPOs, ProblemPreviewVO.class);
        for(ProblemPreviewVO item : resultProblemVOs) {
            // TODO 低效率
            item.setCount(solutionMapper.countSubmitFromProblemId(item.getId()));
            item.setAccepted(solutionMapper.countAcceptedFromProblemId(item.getId()));

            List<ProblemTagPO> tagPO = problemTagMapper.getListFromProblemId(item.getId());
            if(tagPO.size() > 0) {
                List<ProblemTagVO> tagVO = ListCopyUtil.copy(tagPO, ProblemTagVO.class);
                item.setTag(tagVO);
            }

            ProblemClassPO classPO = problemClassMapper.getFromId(item.getId());
            if(classPO != null) {
                ProblemClassPreviewVO classVO = new ProblemClassPreviewVO();
                BeanUtils.copyProperties(classPO, classVO);
                item.setProblemClass(classVO);
            }

            if(requestDTO.getUserId() != null) {
                item.setStatus(problemMapper.getStatus(item.getId(), requestDTO.getUserId()));
            }
        }

        int count = problemMapper.count(requestDTO.getDifficulty(), requestDTO.getTagId(), requestDTO.getClassId(),
            requestDTO.getSearchText(), isSearchId);
        result.setProblemList(resultProblemVOs);
        result.setCount(count);
        return result;
    }

    @Override
    public int countByPublic(ProblemCountDTO requestDTO) {
        return problemMapper.count(requestDTO.getDifficulty(), requestDTO.getTagId(),
                requestDTO.getTagId(), null, null);
    }

    @Override
    public ProblemDetailVO getProblemDetail(long problemId) {
        ProblemDetailVO result = new ProblemDetailVO();

        ProblemPO problem = problemMapper.get(problemId);
        if(problem == null) {
            return null;
        }
        BeanUtils.copyProperties(problem, result);

        int submitCount = solutionMapper.countSubmitFromProblemId(problemId);
        int acceptedCount = solutionMapper.countAcceptedFromProblemId(problemId);
        result.setCount(submitCount);
        result.setAccepted(acceptedCount);

        if(problem.getClassId() != null) {
            ProblemClassPO problemClass = problemClassMapper.getFromId(problem.getClassId());
            ProblemClassPreviewVO problemClassPreviewVO = new ProblemClassPreviewVO();
            BeanUtils.copyProperties(problemClass, problemClassPreviewVO);
            result.setProblemClass(problemClassPreviewVO);
        }

        List<ProblemTagPO> problemTags = problemTagMapper.getListFromProblemId(problemId);
        if(problemTags.size() > 0) {
            List<ProblemTagVO> problemTagVOs = ListCopyUtil.copy(problemTags, ProblemTagVO.class);
            result.setTag(problemTagVOs);
        }

        List<ProblemLimitPO> problemLimits = problemLimitMapper.getListByProblemId(problemId);
        if(problemLimits.size() > 0) {
            List<CurrentProblemLimitVO> currentProblemLimitVOS =
                    ListCopyUtil.copy(problemLimits, CurrentProblemLimitVO.class);
            result.setLimit(currentProblemLimitVOS);
        }

        return result;
    }

}
