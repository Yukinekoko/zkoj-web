package indi.snowmeow.zkoj.base.config;

import indi.snowmeow.zkoj.base.util.IpAddressUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller层网络请求统一日志处理切面
 * @author snowmeow
 * @date 2021/2/3
 */
@Aspect
@Component
public class ControllerLogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerLogAspect.class);

    @Pointcut("execution(public * indi.snowmeow.zkoj.base.controller.*.*(..))")
    public void controllerLog() {

    }

    @Around("controllerLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {

        // 获取当前请求对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        StringBuffer logMessage = new StringBuffer()
                .append(request.getRequestURL()).append(" ")
                .append(request.getMethod()).append(";")
                .append(joinPoint.getTarget().getClass().getName()).append(".")
                .append(joinPoint.getSignature().getName()).append(";")
                .append(IpAddressUtil.getIpAddress(request));
        LOGGER.info(logMessage.toString());

        return joinPoint.proceed();
    }

    /**
     * 获取请求参数
     * */
    private List<Map<String, Object>> getParam(Method method, Object[] args) {
        List<Map<String, Object>> requestParams = new ArrayList<>();
        Parameter[] methodParams = method.getParameters();
        for(int i = 0; i < methodParams.length; i++) {
            //@RequestBody参数
            RequestBody requestBody = methodParams[i].getAnnotation(RequestBody.class);
            if(requestBody != null) {
                Object param = args[i];
                requestParams.addAll(getParamsForRequestBody(param));
            }
            //@RequestParam参数
            RequestParam requestParam = methodParams[i].getAnnotation(RequestParam.class);
            if(requestParam != null) {
                requestParams.add(getParamForRequestParam(requestParam, args[i]));
            }
        }
        return requestParams;
    }

    /**
     * 当参数被@RequestBody修饰时，获取请求参数
     * */
    private List<Map<String, Object>> getParamsForRequestBody(Object arg) {
        List<Map<String, Object>> params = new ArrayList<>();
        if(arg instanceof Map) {
            Map bodyMap = (Map) arg;
            for(Object key : bodyMap.keySet()) {
                Map<String, Object> paramMap = new HashMap<>(1);
                paramMap.put((String) key, bodyMap.get(key));
                params.add(paramMap);
            }
        } else {
            // TODO
        }
        return params;
    }

    /**
     * 当参数被@RequestParam修饰时，获取请求参数
     * */
    private Map<String, Object> getParamForRequestParam(RequestParam requestParam, Object arg) {
        Map<String, Object> param = new HashMap<>(1);
        String paramName = requestParam.name();
        param.put(paramName, arg);
        return param;
    }


}
