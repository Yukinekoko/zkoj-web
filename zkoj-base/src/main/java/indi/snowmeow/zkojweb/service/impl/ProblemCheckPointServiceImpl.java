package indi.snowmeow.zkojweb.service.impl;

import indi.snowmeow.zkojweb.mapper.CheckPointMapper;
import indi.snowmeow.zkojweb.mapper.ProblemMapper;
import indi.snowmeow.zkojweb.model.vo.ProblemCheckPointPreviewVO;
import indi.snowmeow.zkojweb.model.po.ProblemCheckPointPO;
import indi.snowmeow.zkojweb.service.ProblemCheckPointService;
import indi.snowmeow.zkojweb.util.ListCopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author snowmeow
 * @date 2021/2/21
 */
@Service
public class ProblemCheckPointServiceImpl implements ProblemCheckPointService {

    @Autowired
    CheckPointMapper checkPointMapper;
    @Autowired
    ProblemMapper problemMapper;

    @Override
    public List<ProblemCheckPointPreviewVO> getFromProblemId(Long problemId) {
        List<ProblemCheckPointPO> checkPointPOs = checkPointMapper.getFromProblemId(problemId);
        List<ProblemCheckPointPreviewVO> result = ListCopyUtil.copy(checkPointPOs, ProblemCheckPointPreviewVO.class);
        return result;
    }

}
