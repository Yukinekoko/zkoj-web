package indi.snowmeow.zkoj.judger.service.impl;

import indi.snowmeow.zkoj.base.common.enums.SolutionStatusEnum;
import indi.snowmeow.zkoj.judger.dao.SolutionMapper;
import indi.snowmeow.zkoj.judger.service.SolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author snowmeow
 * @date 2021/5/11
 */
@Service
public class SolutionServiceImpl implements SolutionService {

    @Autowired
    SolutionMapper solutionMapper;

    @Override
    public String findSourceCodeFromSolutionId(long solutionId) {
        return solutionMapper.findSourceCodeFromSolutionId(solutionId);
    }

    @Override
    public void updateSolutionStatus(long solutionId, SolutionStatusEnum solutionStatus) {
        solutionMapper.updateStatusId(solutionId, solutionStatus.getId());
    }

    @Override
    public void updateTimeAndMemory(long solutionId, int time, int memory) {
        solutionMapper.updateTimeAndMemory(solutionId, time, memory);
    }
}
