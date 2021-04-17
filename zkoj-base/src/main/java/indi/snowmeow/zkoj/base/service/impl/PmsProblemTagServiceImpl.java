package indi.snowmeow.zkoj.base.service.impl;

import indi.snowmeow.zkoj.base.dao.PmsProblemTagMapper;
import indi.snowmeow.zkoj.base.model.entity.PmsProblemTag;
import indi.snowmeow.zkoj.base.service.PmsProblemTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author snowmeow
 * @date 2021/4/16
 */
@Service
public class PmsProblemTagServiceImpl implements PmsProblemTagService {

    @Autowired
    PmsProblemTagMapper pmsProblemTagMapper;

    @Override
    public List<PmsProblemTag> listFromProblemId(long problemId) {
        return pmsProblemTagMapper.listFromProblemId(problemId);
    }
}
