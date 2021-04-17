package indi.snowmeow.zkoj.base.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import indi.snowmeow.zkoj.base.model.entity.PmsProblemTag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author snowmeow
 * @date 2021/4/16
 */
public interface PmsProblemTagMapper extends BaseMapper<PmsProblemTag> {

    /**
     * 根据问题ID获取标签列表
     * */
    List<PmsProblemTag> listFromProblemId(@Param("problem_id") long problemId);
}
