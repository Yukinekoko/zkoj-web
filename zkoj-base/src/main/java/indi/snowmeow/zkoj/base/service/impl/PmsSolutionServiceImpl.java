package indi.snowmeow.zkoj.base.service.impl;

import indi.snowmeow.zkoj.base.dao.PmsSolutionMapper;
import indi.snowmeow.zkoj.base.service.PmsSolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author snowmeow
 * @date 2021/4/16
 */
@Service
public class PmsSolutionServiceImpl implements PmsSolutionService {

    @Autowired
    PmsSolutionMapper pmsSolutionMapper;

    @Override
    public int countSubmitFromProblemId(long problemId) {
        return pmsSolutionMapper.countSubmitFromProblemId(problemId);
    }

    @Override
    public int countAcceptedFromProblemId(long problemId) {
        return pmsSolutionMapper.countAcceptedFromProblemId(problemId);
    }

    @Override
    public byte getPreviewStatus(long problemId, long userId) {
        return pmsSolutionMapper.getPreviewStatus(problemId, userId);
    }
}
