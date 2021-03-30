package indi.snowmeow.zkojweb.config;

import indi.snowmeow.zkojweb.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * @author snowmeow
 * @date 2021/3/22
 */
@Configuration
//@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AccessDeniedEntryPoint accessDeniedEntryPoint;

    @Autowired
    SecurityAuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    SecurityAuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    SecurityUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
            .loginProcessingUrl("/login")
            .usernameParameter("username")
            .passwordParameter("password")
            .successHandler(authenticationSuccessHandler)
            .failureHandler(authenticationFailureHandler)
            .permitAll()

            .and().authorizeRequests()
            .anyRequest()
            .permitAll()

            .and().exceptionHandling()
            .authenticationEntryPoint(accessDeniedEntryPoint)

            .and().csrf().disable()

            .userDetailsService(userDetailsService);
    }

    @Bean
    protected PasswordEncoder getPasswordEncoder() {
        return new SecurityPasswordEncoder();
    }




}
