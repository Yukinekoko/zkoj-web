package indi.snowmeow.zkoj.base.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author snowmeow
 * @date 2021/4/15
 */
@Configuration
public class ZkojWebMvcConfigurer implements WebMvcConfigurer {

    @Autowired
    private RequestMappingHandlerAdapter adapter;

    @PostConstruct
    protected void initArgumentResolvers() {
        List<HandlerMethodArgumentResolver> argumentResolvers = new ArrayList<>();
        argumentResolvers.add(new ZkojServletModelAttributeMethodProcessor(true));
        argumentResolvers.addAll(adapter.getArgumentResolvers());
        adapter.setArgumentResolvers(argumentResolvers);
    }

    //跨域配置
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET","POST","OPTIONS","PUT","DELETE")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }

}
