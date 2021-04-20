package indi.snowmeow.zkoj.base.service;

import indi.snowmeow.zkoj.base.model.entity.PmsSolution;
import indi.snowmeow.zkoj.base.model.vo.SolutionRankVO;

import java.util.List;

/**
 * @author snowmeow
 * @date 2021/4/16
 */
public interface PmsSolutionService {

    /**
     * 获取指定题目的评测总数
     * @param problemId - 题目ID
     * @return count - 评测总数
     * */
    int countSubmitFromProblemId(long problemId);

    /**
     * 获取指定题目的正确评测总数
     * @param problemId - 题目ID
     * @return accepted - 正确评测总数
     * */
    int countAcceptedFromProblemId(long problemId);

    /**
     * 获取用户指定题目的做题状态：0 - 未尝试；1 - AC；2 - 未完成
     * */
    byte getPreviewStatus(long problemId, long userId);

    /**
     * 获取指定用户的所有评测记录
     * */
    List<PmsSolution> getFromUserId(long userId);

}
