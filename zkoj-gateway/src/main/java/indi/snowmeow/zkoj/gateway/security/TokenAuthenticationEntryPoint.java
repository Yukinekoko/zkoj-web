package indi.snowmeow.zkoj.gateway.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 未登录访问处理器
 * @author snowmeow
 * @date 2021/4/8
 */
public class TokenAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenAuthenticationEntryPoint.class);

    @Override
    public Mono<Void> commence(ServerWebExchange serverWebExchange, AuthenticationException e) {
        LOGGER.info("未登录拦截");
        return Mono.defer(() -> {
            ServerHttpResponse response = serverWebExchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        });
    }
}
