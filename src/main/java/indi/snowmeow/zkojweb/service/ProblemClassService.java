package indi.snowmeow.zkojweb.service;

import indi.snowmeow.zkojweb.model.vo.ProblemClassListVO;
import indi.snowmeow.zkojweb.model.vo.ProblemClassVO;
import indi.snowmeow.zkojweb.po.ProblemClassPO;

import java.util.List;

/**
 * @author snowmeow
 * @date 2021/2/20
 */
public interface ProblemClassService {

    List<ProblemClassListVO> list();

    int delete(Long id);

    ProblemClassVO getFromName(String name);

    void update(ProblemClassPO problemClassPO);

    void save(ProblemClassPO problemClassPO);

}
