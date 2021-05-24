package indi.snowmeow.zkoj.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import indi.snowmeow.zkoj.base.common.base.BaseException;
import indi.snowmeow.zkoj.base.common.enums.ResultCodeEnum;
import indi.snowmeow.zkoj.base.dao.PmsSolutionSourceCodeMapper;
import indi.snowmeow.zkoj.base.model.entity.PmsSolutionSourceCode;
import indi.snowmeow.zkoj.base.service.PmsSolutionSourceCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author snowmeow
 * @date 2021/4/24
 */
@Service
public class PmsSolutionSourceCodeServiceImpl implements PmsSolutionSourceCodeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PmsSolutionSourceCodeServiceImpl.class);

    @Autowired
    PmsSolutionSourceCodeMapper pmsSolutionSourceCodeMapper;

    @Override
    public void insert(PmsSolutionSourceCode solutionSourceCodeEntity) {
        if (pmsSolutionSourceCodeMapper.insert(solutionSourceCodeEntity) != 1) {
            LOGGER.error("PmsSolutionSourceCode insert error - {}", solutionSourceCodeEntity.toString());
            throw new BaseException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public PmsSolutionSourceCode findFromSolutionId(long solutionId) {
        QueryWrapper<PmsSolutionSourceCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("solution_id", solutionId);
        return pmsSolutionSourceCodeMapper.selectOne(queryWrapper);
    }
}
