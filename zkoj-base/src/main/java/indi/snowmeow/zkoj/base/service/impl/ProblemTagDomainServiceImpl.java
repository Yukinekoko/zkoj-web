package indi.snowmeow.zkoj.base.service.impl;

import indi.snowmeow.zkoj.base.dao.ProblemTagDomainMapper;
import indi.snowmeow.zkoj.base.model.vo.ProblemTagVO;
import indi.snowmeow.zkoj.base.service.ProblemTagDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author snowmeow
 * @date 2021/4/23
 */
@Service
public class ProblemTagDomainServiceImpl implements ProblemTagDomainService {

    @Autowired
    ProblemTagDomainMapper problemTagDomainMapper;

    @Override
    public List<ProblemTagVO> list() {
        return problemTagDomainMapper.list();
    }
}
