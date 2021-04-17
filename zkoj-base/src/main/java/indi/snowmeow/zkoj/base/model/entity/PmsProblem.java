package indi.snowmeow.zkoj.base.model.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @author snowmeow
 * @date 2021/4/15
 */
@Data
public class PmsProblem implements Serializable {

    private Long id;

    private String title;

    private String description;

    private String sampleInput;

    private String sampleOutput;
    @TableField("is_spj")
    private boolean spj;

    private String hint;
    @TableField("is_private")
    private boolean problemPrivate;

    private Long userId;

    private Date gmtCreate;

    private Date gmtModified;

    private static final long serialVersionUID = 1L;
}