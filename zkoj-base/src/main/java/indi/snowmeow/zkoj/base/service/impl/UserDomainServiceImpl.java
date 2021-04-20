package indi.snowmeow.zkoj.base.service.impl;

import indi.snowmeow.zkoj.base.common.base.BaseException;
import indi.snowmeow.zkoj.base.common.enums.ResultCodeEnum;
import indi.snowmeow.zkoj.base.model.entity.PmsSolution;
import indi.snowmeow.zkoj.base.model.entity.UmsUser;
import indi.snowmeow.zkoj.base.model.vo.UserInfoVO;
import indi.snowmeow.zkoj.base.service.PmsSolutionService;
import indi.snowmeow.zkoj.base.service.SolutionDomainService;
import indi.snowmeow.zkoj.base.service.UmsUserService;
import indi.snowmeow.zkoj.base.service.UserDomainService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * @author snowmeow
 * @date 2021/4/17
 */
@Service
public class UserDomainServiceImpl implements UserDomainService {

    @Autowired
    UmsUserService umsUserService;
    @Autowired
    PmsSolutionService pmsSolutionService;
    @Autowired
    SolutionDomainService solutionDomainService;

    @Override
    public UserInfoVO getInfoFromUsername(String username) {
        UmsUser umsUser = umsUserService.getFromUsername(username);
        if (null == umsUser) {
            throw new BaseException(ResultCodeEnum.USER_ACCOUNT_NOT_EXIST);
        }
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(umsUser, userInfoVO);
        userInfoVO.setCreateDate(umsUser.getGmtCreate());
        userInfoVO.setLastDate(umsUser.getGmtModified());

        List<PmsSolution> solutions = pmsSolutionService.getFromUserId(umsUser.getId());
        int submitCount = 0;
        int acceptedCount = 0;
        for (PmsSolution item : solutions) {
            if (item.getStatusId() == 1) {
                acceptedCount++;
            } else {
                submitCount++;
            }
        }
        userInfoVO.setSubmitCount(submitCount);
        userInfoVO.setAcceptedCount(acceptedCount);
        userInfoVO.setRank(solutionDomainService.getRankFromUserId(umsUser.getId()));
        return userInfoVO;
    }
}
