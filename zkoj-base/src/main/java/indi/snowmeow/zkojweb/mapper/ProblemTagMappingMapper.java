package indi.snowmeow.zkojweb.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author snowmeow
 * @date 2021/2/20
 */
@Mapper
public interface ProblemTagMappingMapper {

    int deleteFromProblemId(Long problemId);
    /**
     * 批量插入
     * */
    int saveList(Long problemId, List<Long> tagIds);
}
