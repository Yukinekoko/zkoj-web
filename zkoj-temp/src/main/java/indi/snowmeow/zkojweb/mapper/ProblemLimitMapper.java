package indi.snowmeow.zkojweb.mapper;

import indi.snowmeow.zkojweb.model.ProblemLimit;
import indi.snowmeow.zkojweb.model.po.ProblemLimitPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * problemLimitMapper
 * @author snowmeow
 * @date 2020/11/11
 * */
@Mapper
public interface ProblemLimitMapper {

    /**
     * 获取问题限制信息
     * @param problemId 问题id
     * @return 限制信息列表
     */
    List<ProblemLimitPO> getListByProblemId(@Param("problem_id") long problemId);

    /**
     * 获取问题限制信息
     * @param problemId 问题id
     * @return 限制信息列表
     */
    List<ProblemLimit> findListByProblemId(@Param("problem_id") long problemId);

    /**
     * 获取指定题目指定语言的限制信息
     * @param problemId 问题id
     * @param languageId 语言id
     * @return 限制信息
     *         返回结果对象中仅包含time以及memory属性
     * */
    ProblemLimit findByProblemIdAndLanguageId(@Param("problem_id") long problemId,
                                              @Param("language_id") long languageId);
    int insertProblemLimit(Long problemId , Long languageId , Integer memory , Integer time);
}
