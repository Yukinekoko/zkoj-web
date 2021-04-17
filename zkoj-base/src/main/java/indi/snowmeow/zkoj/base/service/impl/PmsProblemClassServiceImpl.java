package indi.snowmeow.zkoj.base.service.impl;

import indi.snowmeow.zkoj.base.dao.PmsProblemClassMapper;
import indi.snowmeow.zkoj.base.model.entity.PmsProblemClass;
import indi.snowmeow.zkoj.base.service.PmsProblemClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author snowmeow
 * @date 2021/4/16
 */
@Service
public class PmsProblemClassServiceImpl implements PmsProblemClassService {

    @Autowired
    PmsProblemClassMapper pmsProblemClassMapper;

    @Override
    public PmsProblemClass getFromProblemId(long problemId) {
        return pmsProblemClassMapper.selectById(problemId);
    }
}
