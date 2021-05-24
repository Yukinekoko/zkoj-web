package indi.snowmeow.zkoj.base.model.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.ToString;

/**
 * @author snowmeow
 * @date 2021/4/15
 */
@ToString
@Data
public class PmsSolution implements Serializable {

    private Long id;

    @TableField("problem_id")
    private Long problemId;

    @TableField("user_id")
    private Long userId;

    private Integer time;

    private Integer memory;

    @TableField("submit_date")
    private Date submitDate;

    @TableField("status_id")
    private Long statusId;

    @TableField("language_id")
    private Long languageId;

    @TableField("error_message")
    private String errorMessage;

    @TableField("gmt_modified")
    private Date gmtModified;

    private static final long serialVersionUID = 1L;
}