package indi.snowmeow.zkojweb.config;

import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author snowmeow
 * @date 2021/2/3
 */
@Configuration
public class AopConfig {

    /**
     * 解决aop与shiro的冲突导致@Controller请求无法呗映射的问题
     * 在aop与shiro同时存在的情况下，由于代理问题会导致Controller类下增加了@RequiresRoles
     * 等shiro注解的请求方法无法映射。
     * */
    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator
                = new DefaultAdvisorAutoProxyCreator();
        // 增加这句或将proxyTargetClass设置为true可以解决bug
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
        return defaultAdvisorAutoProxyCreator;
    }
}
