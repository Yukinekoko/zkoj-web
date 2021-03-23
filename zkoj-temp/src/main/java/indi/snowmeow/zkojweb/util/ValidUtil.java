package indi.snowmeow.zkojweb.util;

import org.apache.shiro.util.Assert;
import org.springframework.validation.BindingResult;

import javax.validation.ValidationException;

/**
 * 检验工具类
 * @author snowmeow
 * @date 2021/2/14
 */
public class ValidUtil {

    public static void valid(BindingResult bindingResult) {
        // TODO 日志补充
        Assert.notNull(bindingResult);
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
    }

}
