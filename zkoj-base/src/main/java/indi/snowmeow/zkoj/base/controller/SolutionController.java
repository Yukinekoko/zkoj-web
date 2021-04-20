package indi.snowmeow.zkoj.base.controller;

import indi.snowmeow.zkoj.base.common.base.BaseException;
import indi.snowmeow.zkoj.base.common.base.BaseResult;
import indi.snowmeow.zkoj.base.common.enums.ResultCodeEnum;
import indi.snowmeow.zkoj.base.service.PmsSolutionService;
import indi.snowmeow.zkoj.base.service.SolutionDomainService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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

}
