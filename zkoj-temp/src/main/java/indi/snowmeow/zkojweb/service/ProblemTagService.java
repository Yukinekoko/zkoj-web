package indi.snowmeow.zkojweb.service;

import indi.snowmeow.zkojweb.model.vo.ProblemTagVO;

import java.util.List;

/**
 * @author snowmeow
 * @date 2021/2/20
 */
public interface ProblemTagService {


    ProblemTagVO getFromId(Long id);

    ProblemTagVO getFromName(String name);
    /**
     * @return 新增的tagId
     * */
    long save(String name);
    /**
     * 获取Tag列表
     * */
    List<ProblemTagVO> list();

    int delete(Long id);

    void update(Long id, String name);
}
