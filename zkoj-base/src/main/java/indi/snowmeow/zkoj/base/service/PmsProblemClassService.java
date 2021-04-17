package indi.snowmeow.zkoj.base.service;

import indi.snowmeow.zkoj.base.model.entity.PmsProblemClass;

/**
 * @author snowmeow
 * @date 2021/4/16
 */
public interface PmsProblemClassService {

    /**
     * 获取指定题目的class对象
     * */
    PmsProblemClass getFromProblemId(long problemId);
}
