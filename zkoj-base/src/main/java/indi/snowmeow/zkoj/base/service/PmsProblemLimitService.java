package indi.snowmeow.zkoj.base.service;

import indi.snowmeow.zkoj.base.model.entity.PmsProblemLimit;

import java.util.List;

/**
 * @author snowmeow
 * @date 2021/4/17
 */
public interface PmsProblemLimitService {

    /**
     * 获取指定问题的限制列表
     * */
    List<PmsProblemLimit> listFromProblemId(long problemId);
}
