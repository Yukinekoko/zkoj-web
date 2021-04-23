package indi.snowmeow.zkoj.base.service.impl;

import indi.snowmeow.zkoj.base.common.base.BaseException;
import indi.snowmeow.zkoj.base.common.enums.ResultCodeEnum;
import indi.snowmeow.zkoj.base.common.util.AuthenticationUtil;
import indi.snowmeow.zkoj.base.common.util.BeanUtil;
import indi.snowmeow.zkoj.base.common.util.JwtUtil;
import indi.snowmeow.zkoj.base.dao.SolutionDomainMapper;
import indi.snowmeow.zkoj.base.model.dto.SolutionListSelectDTO;
import indi.snowmeow.zkoj.base.model.entity.*;
import indi.snowmeow.zkoj.base.model.vo.*;
import indi.snowmeow.zkoj.base.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author snowmeow
 * @date 2021/4/19
 */
@Service
public class SolutionDomainServiceImpl implements SolutionDomainService {

    @Autowired
    SolutionDomainMapper solutionDomainMapper;
    @Autowired
    UmsUserService umsUserService;
    @Autowired
    PmsSolutionService pmsSolutionService;
    @Autowired
    PmsSolutionSourceCodeService pmsSolutionSourceCodeService;
    @Autowired
    PmsSolutionStatusService pmsSolutionStatusService;
    @Autowired
    PmsLanguageService pmsLanguageService;
    @Autowired
    PmsProblemService pmsProblemService;

    @Override
    public int getRankFromUserId(long userId) {
        return solutionDomainMapper.getRankFromUserId(userId);
    }

    @Override
    public List<SolutionRankVO> listByRank(int page, int limit) {
        int offset = (page - 1) * limit;
        return solutionDomainMapper.listByRank(limit, offset);
    }

    @Override
    public UserSolutionRankStatisticsVO getUserSolutionRankStatistics(String username) {
        UmsUser user = umsUserService.findFromUsername(username);
        if (user == null) {
            throw new BaseException(ResultCodeEnum.USER_ACCOUNT_NOT_EXIST);
        }
        return solutionDomainMapper.getUserSolutionRankStatistics(user.getId());
    }

    @Override
    public Map<String, Object> getUserSolutionProblemStatistics(String username) {
        UmsUser user = umsUserService.findFromUsername(username);
        if (user == null) {
            throw new BaseException(ResultCodeEnum.USER_ACCOUNT_NOT_EXIST);
        }
        List<Map<String, Object>> daoResultList = solutionDomainMapper.getUserSolutionProblemStatistics(user.getId());
        List<Map<String, Object>> submitList = new LinkedList<>();
        List<Map<String, Object>> acceptedList = new LinkedList<>();
        for (Map<String, Object> item : daoResultList) {
            long statusId = (long) item.get("status_id");
            Map<String, Object> newItem = new HashMap<>();
            newItem.put("id", item.get("problem_id"));
            newItem.put("title", item.get("title"));
            if (statusId == 1) {
                acceptedList.add(newItem);
            }
            submitList.add(newItem);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("submit_problem_list", submitList);
        result.put("accepted_problem_list", acceptedList);
        return result;
    }

    @Override
    public List<SolutionPreviewVO> listPreview(SolutionListSelectDTO requestDTO) {
        requestDTO.setOffset((requestDTO.getPage() - 1) * requestDTO.getLimit());
        return solutionDomainMapper.listPreview(requestDTO);
    }

    @Override
    public SolutionDetailVO getDetail(long solutionId) {
        PmsSolution solutionEntity = pmsSolutionService.findByPublic(solutionId);
        if (null == solutionEntity) {
            return null;
        }
        SolutionDetailVO result = BeanUtil.copy(solutionEntity, SolutionDetailVO.class);
        // sourceCode
        if (AuthenticationUtil.isLogin()) {
            String token = AuthenticationUtil.getToken();
            if (solutionEntity.getUserId().equals(JwtUtil.getUserId(token))) {
                PmsSolutionSourceCode codeEntity
                        = pmsSolutionSourceCodeService.findFromSolutionId(solutionId);
                result.setSourceCode(codeEntity.getSourceCode());
            }

        }
        // status
        PmsSolutionStatus statusEntity
                = pmsSolutionStatusService.find(solutionEntity.getStatusId());
        SolutionStatusVO statusVO = new SolutionStatusVO();
        statusVO.setId(statusEntity.getId());
        statusVO.setShortName(statusEntity.getName());
        statusVO.setName(statusEntity.getIntroduce());
        result.setStatus(statusVO);
        // language
        PmsLanguage languageEntity =
                pmsLanguageService.find(solutionEntity.getLanguageId());
        result.setLanguage(BeanUtil.copy(languageEntity, LanguageVO.class));
        // problem
        PmsProblem problemEntity =
                pmsProblemService.getFromId(solutionEntity.getProblemId());
        result.setProblem(BeanUtil.copy(problemEntity, SolutionProblemVO.class));
        // user
        UmsUser userEntity =
                umsUserService.find(solutionEntity.getUserId());
        result.setUser(BeanUtil.copy(userEntity, SolutionUserVO.class));
        return result;
    }

}
