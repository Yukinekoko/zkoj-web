package indi.snowmeow.zkojweb.mapper;

import indi.snowmeow.zkojweb.model.CheckPoint;
import indi.snowmeow.zkojweb.po.ProblemCheckPointPO;
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

    List<ProblemCheckPointPO> getFromProblemId(Long problemId);

    int insertCheckPoint(Long problemId,String input , String output);

    int deleteCheckPoint(Long problemId);
}
