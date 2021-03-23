package indi.snowmeow.zkojweb.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import indi.snowmeow.zkojweb.model.po.LanguagePO;

/**
 * 对应solution_source_code表
 * @author snowmeow
 * @date 2020/10/28
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SolutionSourceCode {

    /** 唯一标识符 */
    private Long id;
    /** 评测ID */
    private Long solutionId;
    /** 源代码 */
    private String sourceCode;
    /** 语言对象 */
    private LanguagePO language;

    public void setId(Long id) {
        this.id = id;
    }

    public LanguagePO getLanguage() {
        return language;
    }

    public void setLanguage(LanguagePO language) {
        this.language = language;
    }

    public Long getId() {
        return id;
    }

    public Long getSolutionId() {
        return solutionId;
    }

    public void setSolutionId(Long solutionId) {
        this.solutionId = solutionId;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }
}
