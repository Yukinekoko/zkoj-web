package indi.snowmeow.zkoj.auth.config;

import indi.snowmeow.zkoj.auth.security.ZkojTokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.util.Arrays;

/**
 * @author snowmeow
 * @date 2021/4/13
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

  @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        /* 配置token处理链 */
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(
                Arrays.asList(tokenEnhancer(), jwtAccessTokenConverter()));

        endpoints.tokenStore(jwtTokenStore())
                .authenticationManager(authenticationManager)
                .tokenEnhancer(tokenEnhancerChain)
                .allowedTokenEndpointRequestMethods(HttpMethod.POST)
                .pathMapping("/oauth/token", "/auth/login");
    }

    /* 设置客户端信息 */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("zkoj")
                .secret(passwordEncoder.encode("zkoj"))
                .authorizedGrantTypes("password")
                .scopes("public");
    }

    /* 注入使用jwtStore */
    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /* Jwt处理器配置 */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyPair());
        return converter;
    }

    /* RSA证书加载设置 */
    @Bean
    public KeyPair keyPair() {
        ClassPathResource jwtResource = new ClassPathResource("jwt.jks");
        KeyStoreKeyFactory keyStoreKeyFactory =
                new KeyStoreKeyFactory(jwtResource, "snowmeow".toCharArray());
        return keyStoreKeyFactory.getKeyPair("jwt", "snowmeow".toCharArray());
    }

    /* 自定义jwt内容加强器注入 */
    @Bean
    protected TokenEnhancer tokenEnhancer() {
        return new ZkojTokenEnhancer();
    }
}
