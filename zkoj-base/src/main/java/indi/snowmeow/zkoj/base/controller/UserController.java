package indi.snowmeow.zkoj.base.controller;

import indi.snowmeow.zkoj.api.auth.service.EncodePasswordService;
import indi.snowmeow.zkoj.base.common.base.BaseException;
import indi.snowmeow.zkoj.base.common.base.BaseResult;
import indi.snowmeow.zkoj.base.common.enums.ResultCodeEnum;
import indi.snowmeow.zkoj.base.model.dto.UserInfoUpdateDTO;
import indi.snowmeow.zkoj.base.model.req.UserInfoUpdateRequest;
import indi.snowmeow.zkoj.base.model.vo.UserInfoVO;
import indi.snowmeow.zkoj.base.model.vo.UserSolutionRankStatisticsVO;
import indi.snowmeow.zkoj.base.service.SolutionDomainService;
import indi.snowmeow.zkoj.base.service.UmsUserService;
import indi.snowmeow.zkoj.base.service.UserDomainService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author snowmeow
 * @date 2021/4/17
 */
@RestController
public class UserController {

    @Autowired
    private UserDomainService userDomainService;
    @Autowired
    private UmsUserService umsUserService;
    @Autowired
    private SolutionDomainService solutionDomainService;

    /**
     * 获取用户信息接口
     * @param username
     * @return UserInfo
     */
    @GetMapping("/user/id/{username}")
    public BaseResult<UserInfoVO> getUserInfo(@PathVariable String username){
        return BaseResult.success(userDomainService.getInfoFromUsername(username));
    }

    /**
     * 更新个人信息
     * */
    @PutMapping("/user")
    public BaseResult<Void> updateUserInfo(@Valid @RequestBody UserInfoUpdateRequest request) {
        if (null == request.getName() && null == request.getEmail() && null == request.getDescription()) {
            return BaseResult.fail(ResultCodeEnum.PARAM_ERROR);
        }
        UserInfoUpdateDTO requestDTO = new UserInfoUpdateDTO();
        BeanUtils.copyProperties(request, requestDTO);
        umsUserService.updateBaseInfo(requestDTO);
        return BaseResult.success();
    }

    @GetMapping("/user/count")
    public BaseResult<UserSolutionRankStatisticsVO> getRankStatistics(@RequestParam("username") String username) {
        return BaseResult.success(solutionDomainService.getUserSolutionRankStatistics(username));
    }

    @GetMapping("/user/count/problem")
    public BaseResult<Map<String, Object>> getSolutionProblemStatistics(@RequestParam("username") String username) {
        return BaseResult.success(solutionDomainService.getUserSolutionProblemStatistics(username));
    }

    @PutMapping("/user/password")
    public BaseResult<Void> updatePassword(@RequestBody Map<String, Object> requestData) {
        String password = (String) requestData.get("password");
        String newPassword = (String) requestData.get("new_password");
        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(newPassword)) {
            throw new BaseException(ResultCodeEnum.PARAM_ERROR);
        }
        if (password.length() != 32 || newPassword.length() != 32) {
            throw new BaseException(ResultCodeEnum.PARAM_ERROR);
        }
        umsUserService.updatePassword(password, newPassword);
        return BaseResult.success();
    }
}
