package indi.snowmeow.zkojweb.mapper;

import indi.snowmeow.zkojweb.model.ProblemTag;
import indi.snowmeow.zkojweb.model.po.ProblemTagPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author snowmeow
 * @date 2020/10/21
 */
@Mapper
public interface ProblemTagMapper {

    /**
     * 获取指定题目的算法标签列表
     * @param problemId - 题目ID
     * */
    List<ProblemTagPO> getListFromProblemId(@Param("problem_id") long problemId);

    /**
     * 获取指定题目的算法标签列表
     * @param problemId - 题目ID
     * @return (id, name) - 算法标签列表
     * */
    public List<ProblemTag> getTagListFromProblemId(@Param("problem_id") long problemId);

    /**
     * 获取拥有指定算法标签的题目ID列表
     * @param tagId - 算法标签ID
     * @return 题目ID列表
     * */
    public List<Long> getProblemIdListFromTagId(@Param("tag_id") long tagId);
    /**
     * 获取所有算法标签信息
     * @return 算法标签对象
     */
    List<ProblemTagPO> list();

    int save(ProblemTagPO problemTag);

    ProblemTagPO getFromName(String name);

    int update(ProblemTagPO problemTag);

    ProblemTagPO getFromId(Long id);

    public int insertProblemTag(Long problemId,Long tagId);

    public int insertProblemTagList(Long tagId, Long problemId);

    int delete(Long tagId);
}
