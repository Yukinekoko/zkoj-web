package indi.snowmeow.zkoj.base.config;

import org.springframework.util.Assert;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

import javax.servlet.ServletRequest;

/**
 * 自定义ModelAttributeMethodProcessor
 * @author snowmeow
 * @date 2021/4/15
 */
public class ZkojServletModelAttributeMethodProcessor extends ServletModelAttributeMethodProcessor {


    public ZkojServletModelAttributeMethodProcessor(boolean annotationNotRequired) {
        super(annotationNotRequired);
    }

    @Override
    protected void bindRequestParameters(WebDataBinder binder, NativeWebRequest request) {
        ServletRequest servletRequest = request.getNativeRequest(ServletRequest.class);
        Assert.state(servletRequest != null, "No ServletRequest");
        new CamelServletRequestDataBinder(binder.getTarget(), binder.getObjectName()).bind(servletRequest);
    }
}
