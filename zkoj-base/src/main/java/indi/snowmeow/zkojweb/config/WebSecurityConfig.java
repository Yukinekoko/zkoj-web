package indi.snowmeow.zkojweb.config;

import indi.snowmeow.zkojweb.security.AccessDeniedEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * @author snowmeow
 * @date 2021/3/22
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
            .loginPage("/login")

            .and().exceptionHandling()
            .authenticationEntryPoint(getAuthenticationEntryPoint());
    }

    //@Bean
    protected AuthenticationEntryPoint getAuthenticationEntryPoint() {
        return new AccessDeniedEntryPoint();
    }
}
