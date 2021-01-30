package indi.snowmeow.zkojweb.mapper;

import indi.snowmeow.zkojweb.model.CheckPoint;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * checkPointMapper
 * @author snowmeow
 * @date 2020/11/11
 */
@Mapper
public interface CheckPointMapper {

    /**
     * 根据题目id获取指定题目的测试点数据列表
     * @param problemId 问题id
     * @return 测试点列表
   * */
    List<CheckPoint> findListByProblemId(@Param("problem_id") Long problemId);

    int insertCheckPoint(Long problemId,String input , String output);

    int deleteCheckPoint(Long problemId);

    List<Map<String, Object>> getProblemCheckPoint(Long problemId);
}
