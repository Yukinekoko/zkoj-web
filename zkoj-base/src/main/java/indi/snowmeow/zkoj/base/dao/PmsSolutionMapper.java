package indi.snowmeow.zkoj.base.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import indi.snowmeow.zkoj.base.model.dto.SolutionListSelectDTO;
import indi.snowmeow.zkoj.base.model.entity.PmsSolution;
import indi.snowmeow.zkoj.base.model.vo.SolutionRankVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author snowmeow
 * @date 2021/4/16
 */
public interface PmsSolutionMapper extends BaseMapper<PmsSolution> {


    /**
     * 获取评测对象（公开题目）
     * */
    PmsSolution findByPublic(long id);
    /**
     * 获取指定题目的评测总数
     * @param problemId - 题目ID
     * @return count - 评测总数
     * */
    int countSubmitFromProblemId(@Param("problem_id") long problemId);

    /**
     * 获取指定题目的正确评测总数
     * @param problemId - 题目ID
     * @return accepted - 正确评测总数
     * */
    int countAcceptedFromProblemId(@Param("problem_id") long problemId);

    /**
     * 获取用户指定题目的做题状态：0 - 未尝试；1 - AC；2 - 未完成
     * */
    byte getPreviewStatus(@Param("problem_id") long problemId, @Param("user_id") long userId);

    /**
     * 获取公开题目的评测总数
     * */
    int countByPublic(SolutionListSelectDTO requestDTO);
}
