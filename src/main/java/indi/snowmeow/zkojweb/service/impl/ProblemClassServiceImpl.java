package indi.snowmeow.zkojweb.service.impl;

import indi.snowmeow.zkojweb.mapper.ProblemClassMapper;
import indi.snowmeow.zkojweb.mapper.ProblemMapper;
import indi.snowmeow.zkojweb.po.ProblemClassPO;
import indi.snowmeow.zkojweb.service.ProblemClassService;
import indi.snowmeow.zkojweb.util.ListCopyUtil;
import indi.snowmeow.zkojweb.vo.ProblemClassListVO;
import indi.snowmeow.zkojweb.vo.ProblemClassVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author snowmeow
 * @date 2021/2/20
 */
@Service
public class ProblemClassServiceImpl implements ProblemClassService {

    @Autowired
    ProblemClassMapper problemClassMapper;
    @Autowired
    ProblemMapper problemMapper;

    @Override
    public List<ProblemClassListVO> list() {
        List<ProblemClassListVO> result;
        List<ProblemClassPO> classPOs = problemClassMapper.list();
        result = ListCopyUtil.copy(classPOs, ProblemClassListVO.class);
        for(ProblemClassListVO item : result) {
            item.setCount(problemMapper.count(null, null, item.getId(), null, null));
        }
        return result;
    }
}
