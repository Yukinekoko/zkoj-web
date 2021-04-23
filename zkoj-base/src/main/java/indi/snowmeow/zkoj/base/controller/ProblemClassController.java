package indi.snowmeow.zkoj.base.controller;

import indi.snowmeow.zkoj.base.common.base.BaseResult;
import indi.snowmeow.zkoj.base.model.vo.ProblemClassVO;
import indi.snowmeow.zkoj.base.service.ProblemClassDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author snowmeow
 * @date 2021/4/24
 */
@RestController
public class ProblemClassController {

    @Autowired
    ProblemClassDomainService problemClassDomainService;

    @GetMapping("/problem-class")
    public BaseResult<List<ProblemClassVO>> getList() {
        return BaseResult.success(problemClassDomainService.list());
    }
}
