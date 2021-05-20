package indi.snowmeow.zkoj.judger.service;

import indi.snowmeow.zkoj.base.common.enums.SolutionStatusEnum;

/**
 * @author snowmeow
 * @date 2021/5/11
 */
public interface SolutionService {

    String findSourceCodeFromSolutionId(long solutionId);

    void updateSolutionStatus(long solutionId, SolutionStatusEnum solutionStatus);

    void updateTimeAndMemory(long solutionId, int time, int memory);
}
