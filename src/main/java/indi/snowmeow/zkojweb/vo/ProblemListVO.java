package indi.snowmeow.zkojweb.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

/**
 * 问题列表
 * @author snowmeow
 * @date 2021/2/14
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProblemListVO {

    /** 问题对象列表 */
    private List<ProblemPreviewVO> problemList;
    /** 问题总数 */
    private Integer count;

    @Override
    public String toString() {
        return "ProblemListVO{" +
                "problemList=" + problemList +
                ", count=" + count +
                '}';
    }

    public List<ProblemPreviewVO> getProblemList() {
        return problemList;
    }

    public void setProblemList(List<ProblemPreviewVO> problemList) {
        this.problemList = problemList;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
