package indi.snowmeow.zkoj.base.service.impl;

import indi.snowmeow.zkoj.base.dao.ProblemClassDomainMapper;
import indi.snowmeow.zkoj.base.model.vo.ProblemClassVO;
import indi.snowmeow.zkoj.base.service.ProblemClassDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author snowmeow
 * @date 2021/4/24
 */
@Service
public class ProblemClassDomainServiceImpl implements ProblemClassDomainService {

    @Autowired
    ProblemClassDomainMapper problemClassDomainMapper;

    @Override
    public List<ProblemClassVO> list() {
        return problemClassDomainMapper.list();
    }
}
