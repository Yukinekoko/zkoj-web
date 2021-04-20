package indi.snowmeow.zkoj.base.dao;

import indi.snowmeow.zkoj.base.model.vo.SolutionRankVO;
import indi.snowmeow.zkoj.base.model.vo.UserSolutionRankStatisticsVO;

import java.util.List;
import java.util.Map;

/**
 * @author snowmeow
 * @date 2021/4/19
 */
public interface SolutionDomainMapper {

    /**
     * 获取指定用户的AC数量排名
     * */
    int getRankFromUserId(long userId);

    /**
     * 获取根据AC数排名的Rank列表
     * */
    List<SolutionRankVO> listByRank(int limit, int offset);

    /**
     * 获取指定用户的排名统计信息
     * */
    UserSolutionRankStatisticsVO getUserSolutionRankStatistics(long userId);

    /**
     * 获取指定用户的问题统计信息
     * @return Map:
     *          - problem_id: 问题ID
     *          - title: 问题标题
     *          - status_id: 评测状态ID
     * */
    List<Map<String, Object>> getUserSolutionProblemStatistics(long userId);
}
