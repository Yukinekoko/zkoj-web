package indi.snowmeow.zkoj.base.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author snowmeow
 * @date 2021/2/12
 */
public class ListCopyUtil {

    /**
     * 复制List类型的Bean
     * @param <T> 转换后的类型
     * @param sourceList 需要转换的源列表
     * @param targetClass 转换后的Bean类型Class对象
     * @return 转换后的列表对象
     * */
    public static <T> List<T> copy(List<?> sourceList, Class<T> targetClass) {
        Assert.notNull(sourceList, "Source must not be null");

        List<T> targetList = new ArrayList<>();
        sourceList.forEach(source -> {
            T target = null;
            try {
                target = targetClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new FatalBeanException("Could not copy list");
            }
            BeanUtils.copyProperties(source, target);
            targetList.add(target);
        });
        return targetList;
    }


}
