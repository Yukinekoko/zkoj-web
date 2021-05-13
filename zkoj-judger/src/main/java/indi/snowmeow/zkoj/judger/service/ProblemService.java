package indi.snowmeow.zkoj.judger.service;

import indi.snowmeow.zkoj.judger.model.entity.PmsProblemCheckPoint;

import java.util.List;

/**
 * @author snowmeow
 * @date 2021/5/11
 */
public interface ProblemService {

    List<PmsProblemCheckPoint> findCheckPointListFromProblemId(long problemId);
}
