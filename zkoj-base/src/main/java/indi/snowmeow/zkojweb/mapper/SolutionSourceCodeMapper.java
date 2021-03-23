package indi.snowmeow.zkojweb.mapper;

import indi.snowmeow.zkojweb.model.SolutionSourceCode;
import org.apache.ibatis.annotations.Mapper;

/**
 * SolutionSourceCodeMapper
 * @author snowmeow
 * @date 2020/10/28
 * */
@Mapper
public interface SolutionSourceCodeMapper {

    /**
     * 插入新的评测源代码信息
     * @param SolutionSourceCode - 评测源代码对象
     * @return 影响条目
     * */
    public int insert(SolutionSourceCode solutionSourceCode);

}
