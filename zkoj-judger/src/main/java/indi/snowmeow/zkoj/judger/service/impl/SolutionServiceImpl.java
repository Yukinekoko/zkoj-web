package indi.snowmeow.zkoj.judger.service.impl;

import indi.snowmeow.zkoj.judger.dao.SolutionMapper;
import indi.snowmeow.zkoj.judger.service.SolutionService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author snowmeow
 * @date 2021/5/11
 */
public class SolutionServiceImpl implements SolutionService {

    @Autowired
    SolutionMapper solutionMapper;

    @Override
    public String findSourceCodeFromSolutionId(long solutionId) {
        return solutionMapper.findSourceCodeFromSolutionId(solutionId);
    }
}
