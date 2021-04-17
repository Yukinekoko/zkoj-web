package indi.snowmeow.zkoj.base.service.impl;

import com.alibaba.druid.util.StringUtils;
import indi.snowmeow.zkoj.base.dao.ProblemDomainMapper;
import indi.snowmeow.zkoj.base.model.dto.ProblemListRequestDTO;
import indi.snowmeow.zkoj.base.model.entity.PmsProblem;
import indi.snowmeow.zkoj.base.model.entity.PmsProblemClass;
import indi.snowmeow.zkoj.base.model.entity.PmsProblemTag;
import indi.snowmeow.zkoj.base.model.req.ProblemListRequest;
import indi.snowmeow.zkoj.base.model.vo.ProblemClassPreviewVO;
import indi.snowmeow.zkoj.base.model.vo.ProblemClassVO;
import indi.snowmeow.zkoj.base.model.vo.ProblemPreviewVO;
import indi.snowmeow.zkoj.base.model.vo.ProblemTagVO;
import indi.snowmeow.zkoj.base.service.*;
import indi.snowmeow.zkoj.base.util.ListCopyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author snowmeow
 * @date 2021/4/16
 */
@Service
public class ProblemDomainServiceImpl implements ProblemDomainService {

    @Autowired
    PmsProblemService pmsProblemService;
    @Autowired
    PmsSolutionService pmsSolutionService;
    @Autowired
    PmsProblemTagService pmsProblemTagService;
    @Autowired
    PmsProblemClassService pmsProblemClassService;
    @Autowired
    ProblemDomainMapper problemDomainMapper;

    @Override
    public List<ProblemPreviewVO> getPreviewList(ProblemListRequestDTO request) {
        List<ProblemPreviewVO> result;
        if (StringUtils.isEmpty(request.getSearchText())) {
            result = getPreviewListNormal(request);
        } else {
            try {
                long id = Integer.parseInt(request.getSearchText());
                result = new ArrayList<>();
                result.add(getPreviewFromId(id, request.getUserId()));
            } catch (NumberFormatException e) {
                result = getPreviewListFromSearchTitle(request);
            }
        }
        return result;
    }

    /** 根据条件获取问题列表 */
    protected List<ProblemPreviewVO> getPreviewListNormal(ProblemListRequestDTO request) {
        request.setOffset(request.getLimit() * (request.getPage() - 1));
        return problemDomainMapper.listPreview(request);
    }
    /** 根据标题搜索问题列表 */
    protected List<ProblemPreviewVO> getPreviewListFromSearchTitle(ProblemListRequestDTO request) {
        request.setOffset(request.getLimit() * (request.getPage() - 1));
        request.setSearchText("%" + request.getSearchText() + "%");
        return problemDomainMapper.listPreviewFromSearchTitle(request);
    }
    /** 根据ID获取问题预览 */
    protected ProblemPreviewVO getPreviewFromId(long id, Long userId) {
        PmsProblem pmsProblem = pmsProblemService.getFromId(id);
        if (null == pmsProblem) {
            return null;
        }
        ProblemPreviewVO result = new ProblemPreviewVO();
        BeanUtils.copyProperties(pmsProblem, result);
        PmsProblemClass pmsProblemClass = pmsProblemClassService.getFromProblemId(id);
        if (null != pmsProblemClass) {
            ProblemClassPreviewVO problemClassVO = new ProblemClassPreviewVO();
            BeanUtils.copyProperties(pmsProblemClass, problemClassVO);
            result.setProblemClass(problemClassVO);
        }
        List<PmsProblemTag> pmsProblemTagList = pmsProblemTagService.listFromProblemId(id);
        if (!pmsProblemTagList.isEmpty()) {
            List<ProblemTagVO> pmsProblemTagVOList
                    = ListCopyUtil.copy(pmsProblemTagList, ProblemTagVO.class);
            result.setTag(pmsProblemTagVOList);
        }
        if (null != userId) {
            result.setStatus(pmsSolutionService.getPreviewStatus(id, userId));
        }
        return result;
    }
}
