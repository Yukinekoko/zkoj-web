package indi.snowmeow.zkoj.base.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author snowmeow
 * @date 2021/2/12
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProblemDetailVO {

    /** 唯一标识符 */
    private Long id;
    /** 标题 */
    private String title;
    /** 题目描述 */
    private String introduce;
    /** 样例输入 */
    private String sampleInput;
    /** 样例输出 */
    private String sampleOutput;
    /** 提示 */
    private String hint;
    /** 创建日期 */
    private Date createDate;
    /** 难度ID */
    private Byte difficulty;
    /** 总提交数 */
    private Integer count;
    /** 总AC数 */
    private Integer accepted;
    /** 标签 */
    private List<ProblemTagVO> tag;
    /** 分类 */
    private ProblemClassPreviewVO problemClass;
    /** 限制 */
    private List<CurrentProblemLimitVO> limit;
}
