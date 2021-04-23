package indi.snowmeow.zkoj.base.service.impl;

import indi.snowmeow.zkoj.base.dao.PmsSolutionStatusMapper;
import indi.snowmeow.zkoj.base.model.entity.PmsSolutionStatus;
import indi.snowmeow.zkoj.base.service.PmsSolutionStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author snowmeow
 * @date 2021/4/24
 */
@Service
public class PmsSolutionStatusServiceImpl implements PmsSolutionStatusService {

    @Autowired
    PmsSolutionStatusMapper pmsSolutionStatusMapper;


    @Override
    public PmsSolutionStatus find(long id) {
        return pmsSolutionStatusMapper.selectById(id);
    }
}
