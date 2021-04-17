package indi.snowmeow.zkoj.base.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.web.bind.ServletRequestDataBinder;

import javax.servlet.ServletRequest;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 配置get请求参数下划线转驼峰
 * @author snowmeow
 * @date 2021/4/15
 */
public class CamelServletRequestDataBinder extends ServletRequestDataBinder {

    private static final Logger LOGGER = LoggerFactory.getLogger(CamelServletRequestDataBinder.class);

    private final Pattern underLinePattern = Pattern.compile("_(\\w)");

    public CamelServletRequestDataBinder(Object target) {
        super(target);
    }

    public CamelServletRequestDataBinder(Object target, String objectName) {
        super(target, objectName);
    }

    /**
     * 将请求参数中的下划线命名转换为驼峰命名
     * */
    @Override
    protected void addBindValues(MutablePropertyValues mpvs, ServletRequest request) {
        long startTime = System.currentTimeMillis();
        List<PropertyValue> pvs = mpvs.getPropertyValueList();
        List<PropertyValue> adds = new LinkedList<>();
        for (PropertyValue pv : pvs) {
            String name = pv.getName();
            String camel = underLineToCamel(name);
            if (!name.equals(camel)) {
                adds.add(new PropertyValue(camel, pv.getValue()));
            }
        }
        pvs.addAll(adds);
        long endTime = System.currentTimeMillis();
        LOGGER.info("addBindValues elapsed time - {}", (endTime - startTime));
    }

    private String underLineToCamel(String value) {
        StringBuilder sb = new StringBuilder();
        Matcher m = underLinePattern.matcher(value);
        while (m.find()) {
            m.appendReplacement(sb, m.group(1).toUpperCase());
        }
        m.appendTail(sb);
        return sb.toString();
    }
}
