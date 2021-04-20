package indi.snowmeow.zkoj.base.service;

import indi.snowmeow.zkoj.base.model.vo.SolutionRankVO;
import indi.snowmeow.zkoj.base.model.vo.UserSolutionRankStatisticsVO;

import java.util.List;
import java.util.Map;

/**
 * @author snowmeow
 * @date 2021/4/19
 */
public interface SolutionDomainService {

    /**
     * 获取指定用户的AC数量排名
     * */
    int getRankFromUserId(long userId);

    /**
     * 获取排名列表
     * */
    List<SolutionRankVO> listByRank(int page, int limit);

    /**
     * 获取指定用户的排名统计信息
     * */
    UserSolutionRankStatisticsVO getUserSolutionRankStatistics(String username);

    /**
     * 获取指定用户的问题统计信息
     * @return Map:
     *          - submit_problem_list: 提交问题列表
     *              - id: 问题ID
     *              - title: 问题标题
     *          - accepted_problem_list: AC问题列表
     *              - id: ..
     *              - title: ..
     * */
    Map<String, Object> getUserSolutionProblemStatistics(String username);

}
