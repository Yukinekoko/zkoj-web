package indi.snowmeow.zkojweb.service.impl;

import indi.snowmeow.zkojweb.exception.ApiException;
import indi.snowmeow.zkojweb.mapper.ProblemClassMapper;
import indi.snowmeow.zkojweb.mapper.ProblemMapper;
import indi.snowmeow.zkojweb.model.vo.ProblemClassVO;
import indi.snowmeow.zkojweb.po.ProblemClassPO;
import indi.snowmeow.zkojweb.service.ProblemClassService;
import indi.snowmeow.zkojweb.util.ListCopyUtil;
import indi.snowmeow.zkojweb.model.vo.ProblemClassListVO;
import org.springframework.beans.BeanUtils;
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

    @Override
    public int delete(Long id) {
        return problemClassMapper.delete(id);
    }

    @Override
    public ProblemClassVO getFromName(String name) {
        ProblemClassVO result = new ProblemClassVO();
        ProblemClassPO classPO = problemClassMapper.getFromName(name);
        BeanUtils.copyProperties(classPO, result);
        return result;
    }

    @Override
    public void update(ProblemClassPO problemClassPO) {
        int result = problemClassMapper.update(problemClassPO);
        if (result == 0) {
            throw new ApiException("更新出错");
        }
    }

    @Override
    public void save(ProblemClassPO problemClassPO) {
        int result = problemClassMapper.save(problemClassPO);
        if (result == 0) {
            throw new ApiException("插入出错");
        }
    }
}
