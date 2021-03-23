package indi.snowmeow.zkojweb.mapper;

import indi.snowmeow.zkojweb.model.po.ProblemCheckPointPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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
