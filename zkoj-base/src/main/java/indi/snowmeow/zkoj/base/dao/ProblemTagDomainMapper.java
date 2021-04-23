package indi.snowmeow.zkoj.base.dao;

import indi.snowmeow.zkoj.base.model.vo.ProblemTagVO;

import java.util.List;

/**
 * @author snowmeow
 * @date 2021/4/23
 */
public interface ProblemTagDomainMapper {

    /**
     * 获取所有的标签列表以及每个标签的问题(公开)总数
     * */
    List<ProblemTagVO> list();
}
