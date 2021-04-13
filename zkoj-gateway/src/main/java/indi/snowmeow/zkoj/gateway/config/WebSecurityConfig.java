package indi.snowmeow.zkoj.gateway.config;

import indi.snowmeow.zkoj.gateway.security.TokenAccessDeniedHandler;
import indi.snowmeow.zkoj.gateway.security.TokenAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import reactor.core.publisher.Mono;

/**
 * @author snowmeow
 * @date 2021/4/6
 */
@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfig {

    @Bean
    protected SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange()
            .pathMatchers("/auth/test").authenticated()
            .pathMatchers("/auth/role").hasAuthority("SCOPE_admin")
            .anyExchange().permitAll()
            .and().csrf().disable()
            .oauth2ResourceServer(oAuth2ResourceServerSpec -> {
                oAuth2ResourceServerSpec.authenticationEntryPoint(serverAuthenticationEntryPoint());
                oAuth2ResourceServerSpec.accessDeniedHandler(serverAccessDeniedHandler());
                oAuth2ResourceServerSpec.jwt(jwt -> {
                    JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
                    JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter
                            = new JwtGrantedAuthoritiesConverter();
                    jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
                    jwtConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
                    Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter
                            = new ReactiveJwtAuthenticationConverterAdapter(jwtConverter);
                    jwt.jwtAuthenticationConverter(jwtAuthenticationConverter);
                });
            });

        return http.build();
    }

    @Bean
    protected ServerAccessDeniedHandler serverAccessDeniedHandler() {
        return new TokenAccessDeniedHandler();
    }

    @Bean
    protected ServerAuthenticationEntryPoint serverAuthenticationEntryPoint() {
        return new TokenAuthenticationEntryPoint();
    }



}
