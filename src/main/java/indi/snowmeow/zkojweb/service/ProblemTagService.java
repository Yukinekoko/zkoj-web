package indi.snowmeow.zkojweb.service;

import indi.snowmeow.zkojweb.model.ProblemTag;
import indi.snowmeow.zkojweb.vo.ProblemTagVO;

import java.util.List;

/**
 * @author snowmeow
 * @date 2021/2/20
 */
public interface ProblemTagService {

    /**
     * 获取Tag列表
     * */
    List<ProblemTagVO> list();
}
