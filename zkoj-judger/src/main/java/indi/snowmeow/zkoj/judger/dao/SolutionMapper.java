package indi.snowmeow.zkoj.judger.dao;

/**
 * @author snowmeow
 * @date 2021/5/11
 */
public interface SolutionMapper {

    String findSourceCodeFromSolutionId(long solutionId);
}
