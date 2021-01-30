package indi.snowmeow.zkojweb.shiro;

import indi.snowmeow.zkojweb.shiro.JwtToken;
import indi.snowmeow.zkojweb.util.JwtUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 自定义jwt拦截器
 * @author snowmeow
 * @date 2020/10/13
 */
public class JwtFilter extends AuthenticatingFilter {

    /**
     * 表示是否允许访问。父类会在请求进入拦截器后调用该方法，返回true则继续，否则调用onAccessDenied方法。
     * 这里调用父类的executeLogin()，这个方法首先会调用createToken(),然后调用Subject.login()。
     * */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {

        boolean access = false;
        //请求是否携带token
        String token = ((HttpServletRequest) request).getHeader("Authorization");
        if ( token != null) {
            token = token.replace("Bearer ", "");
            System.out.println("有token");
            //如有则使用executeLogin执行登录检查token是否正确
            try {
                access = executeLogin(request, response);
                if (access) {
                    //是否刷新token
                    //20分钟剩余时间
                    long ttl = 1000 * 60 * 20;
                    if(!JwtUtil.verifyTime(token, ttl)) {
                        String newToken = JwtUtil.refreshToken(token);
                        HttpServletResponse httpResponse = (HttpServletResponse) response;
                        httpResponse.setHeader("Authorization", "Bearer " + newToken);
                        httpResponse.setHeader("Access-Control-Expose-Headers", "Authorization");
                    }
                }
                return access;
            } catch (Exception e) {
                e.printStackTrace();
                return access;
            }
        }
        //如无token，则判断为游客接口，无需验证
        return true;

    }

    /**
     * isAccessAllowed()返回false时执行此方法，表示拒绝访问
     * */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        System.out.println("onAccessDenied");
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return false;
    }

    /**
     * 创建自定义token。
     * executeLogin > createToken > onLoginSuccess >realm验证
     * */
    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        System.out.println("createToken");
        String token = ((HttpServletRequest) servletRequest)
                .getHeader("Authorization")
                .replaceFirst("Bearer ", "");
        return new JwtToken(token);
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        AuthenticationToken token = this.createToken(request, response);
        if (token == null) {
            String msg = "createToken method implementation returned null. A valid non-null AuthenticationToken must be created in order to execute a login attempt.";
            throw new IllegalStateException(msg);
        } else {
            try {
                Subject subject = this.getSubject(request, response);
                subject.login(token);
                return this.onLoginSuccess(token, subject, request, response);
            } catch (AuthenticationException var5) {
                return this.onLoginFailure(token, var5, request, response);
            }
        }
    }

}
