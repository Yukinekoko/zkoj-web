package indi.snowmeow.zkoj.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import indi.snowmeow.zkoj.base.dao.PmsSolutionMapper;
import indi.snowmeow.zkoj.base.model.entity.PmsSolution;
import indi.snowmeow.zkoj.base.model.vo.SolutionRankVO;
import indi.snowmeow.zkoj.base.service.PmsSolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<PmsSolution> getFromUserId(long userId) {
        QueryWrapper<PmsSolution> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return pmsSolutionMapper.selectList(queryWrapper);
    }
}
