package indi.snowmeow.zkojweb.mapper;

import indi.snowmeow.zkojweb.model.Solution;
import indi.snowmeow.zkojweb.model.SolutionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author snowmeow
 * @date 2020/10/20
 */
@Mapper
public interface SolutionMapper {

    /** 
     * 获取指定题目的评测总数
     * @param problemId - 题目ID
     * @return count - 评测总数
     * */
    public int countSubmitFromProblemId(@Param("problem_id") long problemId);

    /**
     * 获取指定题目的正确评测总数
     * @param problemId - 题目ID
     * @return accepted - 正确评测总数
     * */
    public int countAcceptedFromProblemId(@Param("problem_id") long problemId);

    /**
     * 获取指定用户对于指定题目的最终评测状态
     * @param problemId - 题目ID
     * @param userId - 用户ID
     * @return status_id - 最终评测状态
     * */
    public byte getStatusIdFromProblemIdAndUserId(@Param("user_id") long userId, @Param("problem_id") long problemId);

    /**
     * 插入新的solution条目
     * @param solution - solution对象
     * @return 新建的solution列id
     * */
    public long insert(SolutionEntity solution);

    /**
     * 获取评测列表
     * @param limit -返回数量
     * @param userName - 用户名 用为null
     * @param statusId - 评测结果ID 可为null
     * @param problemId - 问题ID 可为null
     * @return 评测列表
     *
     */
    public List<Solution> getSolutionList(@Param("offset") Integer offset,@Param("limit") Integer limit,@Param("userName") String userName,
                                            @Param("statusId") Long statusId , @Param("problemId") Long problemId,@Param("solutionId") Long solutionId);
    /**
     *  获取评测详细
     * @param solutionId - 评测id
     * @param isUser - 判断是否为评测所有者
     * @return 评测详细信息
     */
    public Map<String,Object> getSolutionDetail(@Param("solutionId") Long solutionId,@Param("isUser") Boolean isUser);
    /**
     *  获取评测用户id
     * @param solutionId - 评测id
     * @return userId - 用户id
     */
    public Long getUserIdFromSolutionId(@Param("solutionId") Long solutionId);
    /**
     * 获取评测总数
     * @param problemId - 问题id
     * @param statusId - 状态id
     * @param username - 用户名
     * @return count - 评测总数
     */
    public int getSolutionCount(Long problemId , Long statusId, String username,Long solutionId);
}
