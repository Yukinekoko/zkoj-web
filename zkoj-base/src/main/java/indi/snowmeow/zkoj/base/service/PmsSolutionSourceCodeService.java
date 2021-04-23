package indi.snowmeow.zkoj.base.service;

import indi.snowmeow.zkoj.base.model.entity.PmsSolutionSourceCode;

/**
 * @author snowmeow
 * @date 2021/4/24
 */
public interface PmsSolutionSourceCodeService {

    /**
     * 获取指定评测的源代码类
     * */
    PmsSolutionSourceCode findFromSolutionId(long solutionId);
}
