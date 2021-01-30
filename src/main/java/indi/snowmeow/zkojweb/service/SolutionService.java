package indi.snowmeow.zkojweb.service;

import indi.snowmeow.zkojweb.model.Language;
import indi.snowmeow.zkojweb.model.Problem;
import indi.snowmeow.zkojweb.model.Solution;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * SolutionService
 * @author snowmeow
 * @date 2020/10/28
 */
public interface SolutionService {

    /**
     * 提交评测
     * @param username - 用户名
     * @param problemId - 问题ID
     * @param languageId - 语言ID
     * @param code - 源代码
     * @return 评测ID
     * */
    public Long createSolution(String username, Long problemId, Long languageId,
                          String code);
    /**
     * 获取评测列表
     * @param limit -返回数量
     * @param userName - 用户名 用为null
     * @param statusId - 评测结果ID 可为null
     * @param problemId - 问题ID 可为null
     * @return 评测列表
     *
     */
    public List<Solution> getSolutionList(int offset,int limit,String userName,Long statusId,Long problemId,Long solutionId);
    /**
     *  获取评测详细
     * @param solutionId - 评测id
     * @param isUser - 判断是否为评测所有者
     * @return 评测详细信息
     */
    public Map<String,Object> getSolutionDetail(Long solutionId ,Boolean isUser );
    /**
     *  获取评测用户id
     * @param solutionId - 评测id
     * @return userId - 用户id
     */
    public Long getUserIdFromSolutionId(Long solutionId);
    /**
     *  获取评测总数
     * @param problemId - 问题id
     * @param statusId - 状态id
     * @param username - 用户名
     * @return count 问题总数
     */
    public  int getSolutionCount (Long problemId , Long statusId, String username,Long solutionId);
}
