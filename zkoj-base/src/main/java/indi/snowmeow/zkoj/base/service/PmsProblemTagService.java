package indi.snowmeow.zkoj.base.service;

import indi.snowmeow.zkoj.base.model.entity.PmsProblemTag;

import java.util.List;

/**
 * @author snowmeow
 * @date 2021/4/16
 */
public interface PmsProblemTagService {

    /**
     * 获取指定题目的tag列表
     * */
    List<PmsProblemTag> listFromProblemId(long problemId);
}
