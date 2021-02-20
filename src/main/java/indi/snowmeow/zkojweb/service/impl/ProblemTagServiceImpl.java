package indi.snowmeow.zkojweb.service.impl;

import indi.snowmeow.zkojweb.mapper.ProblemTagMapper;
import indi.snowmeow.zkojweb.model.ProblemTag;
import indi.snowmeow.zkojweb.po.ProblemTagPO;
import indi.snowmeow.zkojweb.service.ProblemTagService;
import indi.snowmeow.zkojweb.util.ListCopyUtil;
import indi.snowmeow.zkojweb.vo.ProblemTagVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author snowmeow
 * @date 2021/2/20
 */
@Service
public class ProblemTagServiceImpl implements ProblemTagService {

    @Autowired
    ProblemTagMapper problemTagMapper;

    @Override
    public List<ProblemTagVO> list() {
        List<ProblemTagPO> tagPOs = problemTagMapper.list();
        return ListCopyUtil.copy(tagPOs, ProblemTagVO.class);
    }
}
