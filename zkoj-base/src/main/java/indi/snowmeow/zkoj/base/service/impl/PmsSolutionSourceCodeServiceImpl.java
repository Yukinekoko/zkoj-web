package indi.snowmeow.zkoj.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import indi.snowmeow.zkoj.base.dao.PmsSolutionSourceCodeMapper;
import indi.snowmeow.zkoj.base.model.entity.PmsSolutionSourceCode;
import indi.snowmeow.zkoj.base.service.PmsSolutionSourceCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author snowmeow
 * @date 2021/4/24
 */
@Service
public class PmsSolutionSourceCodeServiceImpl implements PmsSolutionSourceCodeService {

    @Autowired
    PmsSolutionSourceCodeMapper pmsSolutionSourceCodeMapper;

    @Override
    public PmsSolutionSourceCode findFromSolutionId(long solutionId) {
        QueryWrapper<PmsSolutionSourceCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("solution_id", solutionId);
        return pmsSolutionSourceCodeMapper.selectOne(queryWrapper);
    }
}
