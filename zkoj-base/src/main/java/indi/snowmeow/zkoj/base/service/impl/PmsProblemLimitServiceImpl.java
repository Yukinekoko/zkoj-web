package indi.snowmeow.zkoj.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import indi.snowmeow.zkoj.base.dao.PmsProblemLimitMapper;
import indi.snowmeow.zkoj.base.model.entity.PmsProblemLimit;
import indi.snowmeow.zkoj.base.service.PmsProblemLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author snowmeow
 * @date 2021/4/17
 */
@Service
public class PmsProblemLimitServiceImpl implements PmsProblemLimitService {

    @Autowired
    PmsProblemLimitMapper pmsProblemLimitMapper;

    @Override
    public List<PmsProblemLimit> listFromProblemId(long problemId) {
        QueryWrapper<PmsProblemLimit> queryWrapper = new QueryWrapper<>();
        // queryWrapper.eq("is_private", false);
        queryWrapper.eq("problem_id", problemId);
        return pmsProblemLimitMapper.selectList(queryWrapper);
    }
}
