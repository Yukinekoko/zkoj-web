package indi.snowmeow.zkoj.base.service;

import indi.snowmeow.zkoj.base.model.entity.PmsSolutionStatus;

/**
 * @author snowmeow
 * @date 2021/4/24
 */
public interface PmsSolutionStatusService {

    /**
     * 获取指定评测状态对象
     * */
    PmsSolutionStatus find(long id);
}
