package indi.snowmeow.zkojweb.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import indi.snowmeow.zkoj.util.base.BaseResult;
import indi.snowmeow.zkoj.util.enums.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Spring Security 默认配置下在用户访问未授权url时重定向到登录url，
 * 自定义EntryPint类，当用户访问未授权的url时以Restful Api方式返回错误信息
 * @author snowmeow
 * @date 2021/3/23
 */
@Component
public class AccessDeniedEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    ObjectMapper jsonMapper;

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        BaseResult<Void> result = BaseResult.fail(ResultCodeEnum.USER_NOT_LOGIN);
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.setStatus(403);
        httpServletResponse.getWriter().write(jsonMapper.writeValueAsString(result));
    }
}
