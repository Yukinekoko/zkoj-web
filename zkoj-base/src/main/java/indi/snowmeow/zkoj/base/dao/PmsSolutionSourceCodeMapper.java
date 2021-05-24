package indi.snowmeow.zkoj.base.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import indi.snowmeow.zkoj.base.model.entity.PmsSolutionSourceCode;

/**
 * @author snowmeow
 * @date 2021/4/24
 */
public interface PmsSolutionSourceCodeMapper extends BaseMapper<PmsSolutionSourceCode> {

    /**
     * 插入新的评测源代码对象
     * */
    int insert(PmsSolutionSourceCode solutionSourceCodeEntity);
}
