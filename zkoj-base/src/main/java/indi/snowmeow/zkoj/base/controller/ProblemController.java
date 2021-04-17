package indi.snowmeow.zkoj.base.controller;

import indi.snowmeow.zkoj.base.common.util.AuthenticationUtil;
import indi.snowmeow.zkoj.base.common.util.JwtUtil;
import indi.snowmeow.zkoj.base.model.dto.ProblemListRequestDTO;
import indi.snowmeow.zkoj.base.model.req.ProblemListRequest;
import indi.snowmeow.zkoj.base.service.PmsProblemService;
import indi.snowmeow.zkoj.base.common.base.BaseResult;
import indi.snowmeow.zkoj.base.service.ProblemDomainService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author snowmeow
 * @date 2021/4/15
 */
@RestController
public class ProblemController {

    @Autowired
    ProblemDomainService problemDomainService;

    /**
     * 获取文章列表
     * */
    @GetMapping("/problem")
    public BaseResult<Object> getProblemList(@Valid @ModelAttribute ProblemListRequest request) {
        ProblemListRequestDTO requestDTO = new ProblemListRequestDTO();
        BeanUtils.copyProperties(request, requestDTO);
        String token = AuthenticationUtil.getToken();
        if (null != token) {
            requestDTO.setUserId(JwtUtil.getUserId(token));
        }
        return BaseResult.success(problemDomainService.getPreviewList(requestDTO));
    }

}
