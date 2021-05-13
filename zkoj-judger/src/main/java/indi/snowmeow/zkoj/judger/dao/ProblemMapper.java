package indi.snowmeow.zkoj.judger.dao;

import indi.snowmeow.zkoj.judger.model.entity.PmsProblemCheckPoint;

import java.util.List;

/**
 * @author snowmeow
 * @date 2021/5/11
 */
public interface ProblemMapper {

    List<PmsProblemCheckPoint> findCheckPointListFromProblemId(long problemId);
}
