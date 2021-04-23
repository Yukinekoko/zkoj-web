package indi.snowmeow.zkoj.base.controller;

import indi.snowmeow.zkoj.base.common.base.BaseResult;
import indi.snowmeow.zkoj.base.model.vo.ProblemTagVO;
import indi.snowmeow.zkoj.base.service.ProblemTagDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author snowmeow
 * @date 2021/4/23
 */
@RestController
public class ProblemTagController {

    @Autowired
    ProblemTagDomainService problemTagDomainService;

    @GetMapping("/tag")
    public BaseResult<List<ProblemTagVO>> getList() {
        return BaseResult.success(problemTagDomainService.list());
    }
}
