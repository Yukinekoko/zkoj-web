package indi.snowmeow.zkoj.judger.service.impl;

import indi.snowmeow.zkoj.judger.dao.ProblemMapper;
import indi.snowmeow.zkoj.judger.model.entity.PmsProblemCheckPoint;
import indi.snowmeow.zkoj.judger.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author snowmeow
 * @date 2021/5/11
 */
@Service
public class ProblemServiceImpl implements ProblemService {

    @Autowired
    ProblemMapper problemMapper;

    @Override
    public List<PmsProblemCheckPoint> findCheckPointListFromProblemId(long problemId) {
        return problemMapper.findCheckPointListFromProblemId(problemId);
    }
}
