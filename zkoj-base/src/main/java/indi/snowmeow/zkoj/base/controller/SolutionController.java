package indi.snowmeow.zkoj.base.controller;

import indi.snowmeow.zkoj.base.common.base.BaseException;
import indi.snowmeow.zkoj.base.common.base.BaseResult;
import indi.snowmeow.zkoj.base.common.enums.ResultCodeEnum;
import indi.snowmeow.zkoj.base.common.util.BeanUtil;
import indi.snowmeow.zkoj.base.model.dto.SolutionListSelectDTO;
import indi.snowmeow.zkoj.base.model.dto.SolutionRequestDTO;
import indi.snowmeow.zkoj.base.model.req.SolutionListRequest;
import indi.snowmeow.zkoj.base.model.vo.SolutionDetailVO;
import indi.snowmeow.zkoj.base.model.vo.SolutionPreviewVO;
import indi.snowmeow.zkoj.base.mq.SolutionMessageBinding;
import indi.snowmeow.zkoj.base.service.PmsSolutionService;
import indi.snowmeow.zkoj.base.service.SolutionDomainService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author snowmeow
 * @date 2021/4/19
 */
@RestController
public class SolutionController {

    @Autowired
    PmsSolutionService pmsSolutionService;
    @Autowired
    SolutionDomainService solutionDomainService;

    @GetMapping("/rank")
    public BaseResult<Map<String, Object>> getRankList(@Param("page") int page) {
        if (page <= 0) {
            throw new BaseException(ResultCodeEnum.PARAM_ERROR);
        }
        Map<String, Object> resultData = new HashMap<>();
        resultData.put("rank_list", solutionDomainService.listByRank(page, 50));
        return BaseResult.success(resultData);
    }

    @GetMapping("/solution")
    public BaseResult<Map<String, Object>> getList(@Valid @ModelAttribute SolutionListRequest request) {
        SolutionListSelectDTO requestDTO = BeanUtil.copy(request, SolutionListSelectDTO.class);
        requestDTO.setLimit(50);
        if (requestDTO.getPage() == null) {
            requestDTO.setPage(1);
        }
        List<SolutionPreviewVO> solutions = solutionDomainService.listPreview(requestDTO);
        int count = pmsSolutionService.count(requestDTO);
        Map<String, Object> result = new HashMap<>();
        result.put("solution_list", solutions);
        result.put("count", count);
        return BaseResult.success(result);
    }

    @GetMapping("/solution/{solution_id}")
    public BaseResult<SolutionDetailVO> getDetail(@PathVariable("solution_id") long solutionId) {
        if (solutionId <= 0) {
            throw new BaseException(ResultCodeEnum.PARAM_ERROR);
        }
        return BaseResult.success(solutionDomainService.getDetail(solutionId));
    }

    @PostMapping("/solution/problem/{problem_id}")
    public BaseResult<Void> createNewSolution(@PathVariable("problem_id") long problemId,
                                              @RequestBody Map<String, Object> requestBody) {
        Integer languageId = (Integer) requestBody.get("language_id");
        String code = (String) requestBody.get("code");
        if (code == null || languageId == null || problemId <= 0 || languageId <= 0) {
            throw new BaseException(ResultCodeEnum.PARAM_ERROR);
        }
        solutionDomainService.createSolution(problemId, languageId, code);
        return BaseResult.success();
    }

}
