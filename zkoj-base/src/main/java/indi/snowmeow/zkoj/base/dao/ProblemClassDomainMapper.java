package indi.snowmeow.zkoj.base.dao;

import indi.snowmeow.zkoj.base.model.vo.ProblemClassVO;

import java.util.List;

/**
 * @author snowmeow
 * @date 2021/4/24
 */
public interface ProblemClassDomainMapper {

    /**
     * 获取标签列表，包括每个标签的问题（公开）数量
     * */
    List<ProblemClassVO> list();
}
